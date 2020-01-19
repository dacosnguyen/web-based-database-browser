package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.entity.DBColumn;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

@Service
public class DatabaseBrowserService implements IDatabaseBrowserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBrowserService.class);
    private static final String SCHEMA_PATTERN = "public";
    private final IConnectionDetailService service;

    @Autowired
    public DatabaseBrowserService(IConnectionDetailService service) {
        this.service = service;
    }

    @Override
    public List<String> getAllSchemas(int connectionDetailId) throws NotFoundException, SQLException, ClassNotFoundException {
        final Function<Connection, List<String>> fn = (connection) -> {
            List<String> result = new ArrayList<>();
            try {
                final ResultSet rs = connection.getMetaData().getSchemas();
                while (rs.next()) {
                    // Get schema name
                    result.add(rs.getString(1));
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        };

        return invoke(connectionDetailId, fn);
    }

    @Override
    public List<String> getAllTables(int connectionDetailId) throws SQLException, NotFoundException, ClassNotFoundException {
        final Function<Connection, List<String>> fn = (connection) -> {
            List<String> result = new ArrayList<>();
            try {
                ResultSet rs = connection.getMetaData()
                        // TODO better filtering
                        .getTables(null, SCHEMA_PATTERN, "%", null);
                while (rs.next()) {
                    //  Get table name
                    result.add(rs.getString(3));
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        };

        return invoke(connectionDetailId, fn);
    }

    @Override
    public List<DBColumn> getAllColumns(int connectionDetailId, String tableName) throws SQLException, NotFoundException, ClassNotFoundException {
        final Function<Connection, List<DBColumn>> fn = (connection) -> {
            List<DBColumn> result = new ArrayList<>();
            try {
                ResultSet rs = connection.getMetaData()
                        .getColumns(null, "public", tableName, null);
                while (rs.next()) {
                    final DBColumn column = newColumn(rs);
                    result.add(column);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        };

        return invoke(connectionDetailId, fn);
    }

    @Override
    public DBColumn newColumn(ResultSet rs) throws SQLException {
        return new DBColumn(
                // Get column name
                rs.getString(4),
                // Get data type
                JDBCType.valueOf(rs.getInt(5)),
                // Get nullable
                rs.getString(18)
        );
    }

    @Override
    public List<Map<String, Object>> getTableRows(int connectionDetailId, String tableName, int maxRows) throws SQLException, NotFoundException, ClassNotFoundException {
        final Function<Connection, List<Map<String, Object>>> fn = (connection) -> {
            final List<Map<String, Object>> rowsResult = new ArrayList<>();
            try {
                List<DBColumn> columnList = new ArrayList<>();
                ResultSet metaData = connection.getMetaData()
                        .getColumns(null, "public", tableName, null);
                while (metaData.next()) {
                    final DBColumn column = newColumn(metaData);
                    columnList.add(column);
                }

                final PreparedStatement statement = connection.prepareStatement(String.format("select * from %s limit %d", tableName, maxRows));
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnList.size(); i++) {
                        row.put(columnList.get(i-1).getName(), rs.getObject(i));
                    }
                    rowsResult.add(row);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return rowsResult;
        };

        return invoke(connectionDetailId, fn);
    }

    /**
     * Connects to a database and invokes defined function on the connection.
     * R stands for a result type.
     */
    private <R> R invoke(int connectionDetailId, Function<Connection, R> fn) throws NotFoundException, ClassNotFoundException, SQLException {
        // fetch connection detail from ID
        final ConnectionDetail connectionDetail = service.getConnectionDetail(connectionDetailId);

        R result;
        Class.forName("org.postgresql.Driver");

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("jdbc:postgresql://")
                .append(connectionDetail.getHostname())
                .append(":")
                .append(connectionDetail.getPort())
                .append("/")
                .append(connectionDetail.getDatabaseName())
        ;

        // Creating connection
        Connection connection = DriverManager.getConnection(urlBuilder.toString(),
                "postgres", "");
        DatabaseBrowserService.LOGGER.info(String.format("Connected to the database: %s (%s)", connectionDetail.getName(), connectionDetail.getDescription()));

        result = fn.apply(connection);

        connection.close();
        return result;
    }
}
