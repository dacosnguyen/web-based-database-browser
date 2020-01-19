package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class DatabaseBrowserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBrowserService.class);
    private final ConnectionDetailService connectionDetailService;

    @Autowired
    public DatabaseBrowserService(ConnectionDetailService connectionDetailService) {
        this.connectionDetailService = connectionDetailService;
    }

    public List<String> getAllSchemas(int id) {
        final BiConsumer<Connection, List<String>> fn = (connection, resultList) -> {
            // Getting DatabaseMetaData object
            DatabaseMetaData dbMetaData;
            try {
                dbMetaData = connection.getMetaData();

                // Getting Database Schema Names
                final ResultSet rs = dbMetaData.getSchemas();
                while (rs.next()) {
                    // Get schema name
                    resultList.add(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        return invoke(id, fn);
    }

    public <T> List<T> invoke(int id, BiConsumer<Connection, List<T>> fn) {
        // fetch connection detail from ID
        final Optional<ConnectionDetail> connectionDetail = connectionDetailService.getConnectionDetail(id);
        if (connectionDetail.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> resultList = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");

            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("jdbc:postgresql://")
                    .append(connectionDetail.get().getHostname())
                    .append(":")
                    .append(connectionDetail.get().getPort())
                    .append("/")
                    .append(connectionDetail.get().getDatabaseName())
            ;

            // Creating connection
            Connection connection = DriverManager.getConnection(urlBuilder.toString(),
                    "postgres", "");
            LOGGER.info(String.format("Connected to the database: %s (%s)", connectionDetail.get().getName(), connectionDetail.get().getDescription()));

            fn.accept(connection, resultList);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
