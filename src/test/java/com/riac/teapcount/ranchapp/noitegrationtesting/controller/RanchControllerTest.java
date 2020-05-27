package com.riac.teapcount.ranchapp.controller;

import com.riac.teapcount.ranchapp.domain.model.Ranch;
import com.riac.teapcount.ranchapp.service.RanchServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class RanchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RanchServiceImpl ranchService;

    private final String PATH_RANCHES_ID = "/ranches/1";

    private final Integer RANCH_ID = 1;

    @Test
    public void getRanchById() throws Exception {

        Ranch ranch = Ranch.builder()
                .id(1)
                .name("Riac")
                .city("Pittsburgh")
                .build();

        Mockito.when(ranchService.getRanchById(ArgumentMatchers.anyInt())).thenReturn(ranch);

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/ranches/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "{\"id\": 1,\"name\": \"Riac\",\"city\": \"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

}
