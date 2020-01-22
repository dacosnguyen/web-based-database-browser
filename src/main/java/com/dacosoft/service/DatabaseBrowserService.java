package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.entity.DBColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class DatabaseBrowserService implements IDatabaseBrowserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBrowserService.class);
    private static final String SCHEMA_PATTERN = "PUBLIC";
    private String driverClassName = "org.postgresql.Driver";
    private CheckedBiFunction<IConnectionDetailService, Integer, ConnectionDetail> getConnectionDetailFn = IConnectionDetailService::getConnectionDetail;
    private Function<ConnectionDetail, String> urlConverter = connectionDetail1 -> new StringBuilder()
            .append("jdbc:postgresql://")
            .append(connectionDetail1.getHostname())
            .append(":")
            .append(connectionDetail1.getPort())
            .append("/")
            .append(connectionDetail1.getDatabasename())
            .toString();

    private final IConnectionDetailService service;

    @Autowired
    public DatabaseBrowserService(IConnectionDetailService service) {
        this.service = service;
    }

    @Override
    public List<String> getAllSchemas(int connectionDetailId) throws Exception {
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

        return invoke(fn, driverClassName, urlConverter, getConnectionDetailFn, connectionDetailId);
    }

    @Override
    public List<String> getAllTables(int connectionDetailId) throws Exception {
        final Function<Connection, List<String>> fn = (connection) -> {
            List<String> result = new ArrayList<>();
            try {
                ResultSet rs = connection.getMetaData().getTables(null, SCHEMA_PATTERN, "%", null);
                while (rs.next()) {
                    //  Get table name
                    result.add(rs.getString(3));
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        };

        return invoke(fn, driverClassName, urlConverter, getConnectionDetailFn, connectionDetailId);
    }

    @Override
    public List<DBColumn> getAllColumns(int connectionDetailId, String tableName) throws Exception {
        final Function<Connection, List<DBColumn>> fn = (connection) -> {
            List<DBColumn> result = new ArrayList<>();
            try {
                ResultSet rs = connection.getMetaData()
                        .getColumns(null, SCHEMA_PATTERN, tableName, null);
                while (rs.next()) {
                    final DBColumn column = newColumn(rs);
                    result.add(column);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        };

        return invoke(fn, driverClassName, urlConverter, getConnectionDetailFn, connectionDetailId);
    }

    private DBColumn newColumn(ResultSet rs) throws SQLException {
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
    public List<Map<String, Object>> getTableRows(int connectionDetailId, String tableName, int maxRows) throws Exception {
        final Function<Connection, List<Map<String, Object>>> fn = (connection) -> {
            final List<Map<String, Object>> rowsResult = new ArrayList<>();
            try {
                final PreparedStatement statement = connection.prepareStatement(String.format("select * from %s limit %d", tableName, maxRows));
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                    }
                    rowsResult.add(row);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return rowsResult;
        };

        return invoke(fn, driverClassName, urlConverter, getConnectionDetailFn, connectionDetailId);
    }

    /**
     * Connects to a database and invokes defined function on the connection.
     * R stands for a result type.
     */
    private <R> R invoke(Function<Connection, R> fn, String driverClassName, Function<ConnectionDetail, String> urlConverter, CheckedBiFunction<IConnectionDetailService, Integer, ConnectionDetail> connectionDetailSupplier, int connectionDetailId) throws Exception {
        // fetch connection detail from ID
        final ConnectionDetail connectionDetail = connectionDetailSupplier.apply(service, connectionDetailId);

        R result;

        Class.forName(driverClassName);
        final String username = connectionDetail.getUsername();
        final String password = connectionDetail.getPassword();
        try (Connection connection = DriverManager.getConnection(urlConverter.apply(connectionDetail), username, password)) {
            DatabaseBrowserService.LOGGER.info(String.format("Connected to the database: %s (%s)", connectionDetail.getName(), connectionDetail.getDescription()));

            result = fn.apply(connection);
        }

        return result;
    }

    /**
     * Used primarily for tests.
     */
    protected void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * Used primarily for tests.
     */
    protected void setUrlConverter(Function<ConnectionDetail, String> urlConverter) {
        this.urlConverter = urlConverter;
    }

    /**
     * Used primarily for tests.
     */
    protected void setGetConnectionDetailFn(CheckedBiFunction<IConnectionDetailService, Integer, ConnectionDetail> getConnectionDetailFn) {
        this.getConnectionDetailFn = getConnectionDetailFn;
    }

    @FunctionalInterface
    public interface CheckedBiFunction<S, T, R> {
        R apply(S s, T t) throws Exception;
    }
}
