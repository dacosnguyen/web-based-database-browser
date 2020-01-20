package com.dacosoft.controller;

import com.dacosoft.entity.ConnectionDetail;
import com.dacosoft.service.ConnectionDetailService;
import javassist.NotFoundException;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConnectionDetailController.class)
class ConnectionDetailControllerTest {

    public static final String JSON1 = "src/test/resources/ConnectionDetailControllerTest_1.json";
    public static final String JSON2 = "src/test/resources/ConnectionDetailControllerTest_2.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionDetailService mockedService;

    @Test
    void getAllConnectionDetails() throws Exception {
        List<ConnectionDetail> connectionDetails = new ArrayList<>();
        final ConnectionDetail cd1 = new ConnectionDetail();
        cd1.setId(1);
        cd1.setPassword("pass1");
        cd1.setPort(1111);
        cd1.setUsername("user1");
        cd1.setHostname("hostname1");
        cd1.setDatabaseName("databasename1");
        cd1.setName("name1");
        connectionDetails.add(cd1);
        final ConnectionDetail cd2 = new ConnectionDetail();
        cd2.setId(2);
        cd2.setPassword("pass2");
        cd2.setPort(2222);
        cd2.setUsername("user2");
        cd2.setHostname("hostname2");
        cd2.setDatabaseName("databasename2");
        cd2.setName("name2");
        cd2.setDescription("some description 2");
        connectionDetails.add(cd2);
        when(mockedService.getConnectionDetails()).thenReturn(connectionDetails);

        mockMvc.perform(get(ConnectionDetailController.CONNECTION_DETAILS_PREFIX))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getJsonContent(JSON1)));

        Mockito.verify(mockedService, times(1)).getConnectionDetails();
    }

    @Test
    void getConnectionDetail() throws Exception {
        final ConnectionDetail cd = new ConnectionDetail();
        cd.setId(1);
        cd.setPassword("pass1");
        cd.setPort(1111);
        cd.setUsername("user1");
        cd.setHostname("hostname1");
        cd.setDatabaseName("databasename1");
        cd.setName("name1");
        when(mockedService.getConnectionDetail(1)).thenReturn(cd);

        mockMvc.perform(get(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getJsonContent(JSON2)));

        Mockito.verify(mockedService, times(1)).getConnectionDetail(1);
    }

    @Test
    void getConnectionDetailNotFound() throws Exception {
        when(mockedService.getConnectionDetail(666)).thenThrow(new NotFoundException(""));

        mockMvc.perform(get(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/666"))
                .andExpect(status().isNotFound());

        Mockito.verify(mockedService, times(1)).getConnectionDetail(666);
    }

    @Test
    void saveConnectionDetail() throws Exception {
        final ConnectionDetail cd = new ConnectionDetail();
        cd.setId(1);
        cd.setPassword("pass1");
        cd.setPort(1111);
        cd.setUsername("user1");
        cd.setHostname("hostname1");
        cd.setDatabaseName("databasename1");
        cd.setName("name1");
        Mockito.doNothing().when(mockedService).saveConnectionDetail(cd);

        Mockito.verify(mockedService, times(0)).saveConnectionDetail(cd);

        mockMvc.perform(
                post(ConnectionDetailController.CONNECTION_DETAILS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent(JSON2)))
                .andExpect(status().isOk());

        Mockito.verify(mockedService, times(1)).saveConnectionDetail(cd);
    }

    @Test
    void updateConnectionDetail() throws Exception {
        final ConnectionDetail cd = new ConnectionDetail();
        cd.setId(1);
        cd.setPassword("pass1");
        cd.setPort(1111);
        cd.setUsername("user1");
        cd.setHostname("hostname1");
        cd.setDatabaseName("databasename1");
        cd.setName("name1");
        Mockito.doNothing().when(mockedService).updateConnectionDetail(cd);

        Mockito.verify(mockedService, times(0)).updateConnectionDetail(cd);

        mockMvc.perform(
                put(ConnectionDetailController.CONNECTION_DETAILS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent(JSON2)))
                .andExpect(status().isOk());

        Mockito.verify(mockedService, times(1)).updateConnectionDetail(cd);
    }

    @Test
    void deleteConnectionDetail() throws Exception {
        final int id = 1;
        Mockito.doNothing().when(mockedService).deleteConnectionDetail(id);

        Mockito.verify(mockedService, times(0)).deleteConnectionDetail(id);

        mockMvc.perform(
                delete(ConnectionDetailController.CONNECTION_DETAILS_PREFIX + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent(JSON2)))
                .andExpect(status().isOk());

        Mockito.verify(mockedService, times(id)).deleteConnectionDetail(id);
    }

    private String getJsonContent(String json) throws IOException, ParseException {
        return new JSONParser().parse(new FileReader(json)).toString();
    }
}