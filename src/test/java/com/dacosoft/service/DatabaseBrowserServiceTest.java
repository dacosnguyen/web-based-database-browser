package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.entity.DBColumn;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An integration test which includes a database.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DatabaseBrowserServiceTest {

    private final String CONNECTION_DETAIL_TABLE_NAME = "CONNECTION_DETAIL";
    @Autowired
    private DatabaseBrowserService databaseBrowserService;

    @Autowired
    private Environment env;

    @BeforeEach
    public void init() {
        databaseBrowserService.setDriverClassName("org.h2.Driver");
        databaseBrowserService.setUrlConverter(connectionDetail -> "jdbc:h2:file:~/" + connectionDetail.getDatabasename() + ";IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE");
        final ConnectionDetail connectionDetail = new ConnectionDetail();
        connectionDetail.setUsername(env.getProperty("spring.datasource.username"));
        connectionDetail.setPassword(env.getProperty("spring.datasource.password"));
        connectionDetail.setDatabasename("TESTDB");
        connectionDetail.setName("Test H2 DB");
        connectionDetail.setDescription("Test H2 DB description");
        databaseBrowserService.setGetConnectionDetailFn((service, integer) -> connectionDetail);
    }

    @Test
    void getAllSchemas() throws Exception {
        final List<String> schemas = databaseBrowserService.getAllSchemas(2);
        Assert.assertFalse(schemas.isEmpty());
        Assert.assertEquals("INFORMATION_SCHEMA", schemas.get(0));
        Assert.assertEquals("PUBLIC", schemas.get(1));
    }

    @Test
    void getAllTables() throws Exception {
        final List<String> tables = databaseBrowserService.getAllTables(2);
        final Optional<String> connectionDetailTable = tables.stream().filter(CONNECTION_DETAIL_TABLE_NAME::equals).findFirst();
        Assert.assertTrue(connectionDetailTable.isPresent());
        Assert.assertEquals(CONNECTION_DETAIL_TABLE_NAME, connectionDetailTable.get());
    }

    @Test
    void getAllColumns() throws Exception {
        final List<DBColumn> columns = databaseBrowserService.getAllColumns(2, CONNECTION_DETAIL_TABLE_NAME);
        Assert.assertEquals(8, columns.size());
        Assert.assertEquals("ID", columns.get(0).getName());
        Assert.assertEquals("NAME", columns.get(1).getName());
        Assert.assertEquals("HOSTNAME", columns.get(2).getName());
        Assert.assertEquals("PORT", columns.get(3).getName());
        Assert.assertEquals("DATABASENAME", columns.get(4).getName());
        Assert.assertEquals("USERNAME", columns.get(5).getName());
        Assert.assertEquals("PASSWORD", columns.get(6).getName());
        Assert.assertEquals("DESCRIPTION", columns.get(7).getName());
    }

    @Test
    void getTableRows() throws Exception {
        final List<Map<String, Object>> tableRows = databaseBrowserService.getTableRows(2, CONNECTION_DETAIL_TABLE_NAME, 2);
        Assert.assertEquals(2, tableRows.size());
        tableRows.forEach(map -> {
            Assert.assertEquals(8, map.keySet().size());
            map.forEach((colName, value) -> Assert.assertNotNull(value));
        });
        Assert.assertEquals("Kobra PostgreSQL", tableRows.get(0).get("NAME"));
        Assert.assertEquals("Embedded H2 Database", tableRows.get(1).get("NAME"));
    }
}