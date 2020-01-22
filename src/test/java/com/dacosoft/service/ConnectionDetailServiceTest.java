package com.dacosoft.service;

import com.dacosoft.entity.ConnectionDetail;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * An integration test which includes a database.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionDetailServiceTest {

    @Autowired
    private ConnectionDetailService connectionDetailService;

    @Test
    public void testGetConnectionDetails() {
        final List<ConnectionDetail> connectionDetails = connectionDetailService.getConnectionDetails();
        Assert.assertEquals(1, connectionDetails.get(0).getId().intValue());
        Assert.assertEquals("localhost", connectionDetails.get(0).getHostname());
        Assert.assertEquals(5432, connectionDetails.get(0).getPort());
        Assert.assertEquals("postgres", connectionDetails.get(0).getUsername());
        Assert.assertEquals(2, connectionDetails.get(1).getId().intValue());
    }

    @Test
    public void testGetConnectionDetail() throws NotFoundException {
        final ConnectionDetail cd = connectionDetailService.getConnectionDetail(1);
        Assert.assertEquals(1, cd.getId().intValue());
    }

    @Test(expected = NotFoundException.class)
    public void testGetConnectionDetailNotFound() throws NotFoundException {
        connectionDetailService.getConnectionDetail(666);
    }

    @Test
    public void testSaveConnectionDetail() throws NotFoundException {
        final ConnectionDetail cd = new ConnectionDetail();
        cd.setName("name");
        cd.setDatabasename("main");
        cd.setHostname("localhost");
        cd.setUsername("dacos");
        cd.setPassword("1234");
        cd.setPort(666);
        connectionDetailService.saveConnectionDetail(cd);

        final ConnectionDetail newCd = connectionDetailService.getConnectionDetail(cd.getId());
        Assert.assertEquals(3, newCd.getId().intValue());

        connectionDetailService.deleteConnectionDetail(newCd.getId());
    }

    @Test
    public void testUpdateConnectionDetail() throws NotFoundException {
        final ConnectionDetail cd = connectionDetailService.getConnectionDetail(1);
        final UUID randomUUID = UUID.randomUUID();
        cd.setPassword(randomUUID.toString());

        connectionDetailService.updateConnectionDetail(cd);

        final ConnectionDetail newCd = connectionDetailService.getConnectionDetail(cd.getId());
        Assert.assertEquals(randomUUID.toString(), newCd.getPassword());
    }

    @Test
    public void testDeleteConnectionDetail() {
        final ConnectionDetail cd = new ConnectionDetail();
        cd.setName("name");
        cd.setDatabasename("main");
        cd.setHostname("localhost");
        cd.setUsername("dacos");
        cd.setPassword("1234");
        cd.setPort(666);
        connectionDetailService.saveConnectionDetail(cd);

        ConnectionDetail newCd = null;
        try {
            newCd = connectionDetailService.getConnectionDetail(cd.getId());
        } catch (NotFoundException e) {
            // do nothing
        }
        Assert.assertNotNull(newCd);
        Assert.assertEquals((int) newCd.getId(), newCd.getId().intValue());

        connectionDetailService.deleteConnectionDetail(newCd.getId());

        try {
            connectionDetailService.getConnectionDetail(newCd.getId());
        } catch (NotFoundException e) {
            Assert.assertEquals(String.format("Connection detail with ID %d was not found!", cd.getId()), e.getMessage());
        }

    }
}
