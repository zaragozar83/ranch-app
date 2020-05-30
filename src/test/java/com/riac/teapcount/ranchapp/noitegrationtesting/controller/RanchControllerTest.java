package com.riac.teapcount.ranchapp.noitegrationtesting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riac.teapcount.ranchapp.domain.model.Ranch;
import com.riac.teapcount.ranchapp.service.RanchServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest
public class RanchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RanchServiceImpl ranchService;

    private final String PATH_RANCHES = "/ranches";
    private final String PATH_RANCHES_ID = "/ranches/1";
    private final String PATH_RANCHES_NAME = "/ranches/Riac/name";
    private final String PATH_RANCHES_WRONG_NAME = "/ranches/Riac";
    private final String PATH_RANCHES_CITY = "/ranches/Pittsburgh/city";

    private final Integer RANCH_ID = 1;

    @Test
    void getRanches() throws Exception {


        final List<Ranch> ranchesResponse = initRanches();

        Mockito.when(ranchService.getRanches()).thenReturn(ranchesResponse);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(PATH_RANCHES))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        ObjectMapper mapper = new ObjectMapper();

        List<Ranch> ranchesResult = mapper.readValue(response.getContentAsString(), new TypeReference<List<Ranch>>() {});

        Assertions.assertEquals(ranchesResponse.size(), ranchesResult.size());
        Assertions.assertEquals(ranchesResponse.toString(), ranchesResult.toString());
    }

    @Test
    void getRanchById() throws Exception {

        Mockito.when(ranchService.getRanchById(ArgumentMatchers.anyInt())).thenReturn(initRanchResponse());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get(PATH_RANCHES_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "{\"id\": 1,\"name\": \"Riac\",\"city\": \"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    void getRanchByName() throws Exception{

        Mockito.when(ranchService.getRanchByName(ArgumentMatchers.anyString())).thenReturn(initRanchResponse());

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get(PATH_RANCHES_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "{\"id\": 1,\"name\": \"Riac\",\"city\": \"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

    }

    @Test
    void getRanchByCity() throws Exception {

        List<Ranch> ranches = initPartialRanches();
        Mockito.when(ranchService.getRanchesByCity(ArgumentMatchers.anyString())).thenReturn(ranches);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get(PATH_RANCHES_CITY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "[{\"id\":1,\"name\":\"Riac\",\"city\":\"Pittsburgh\"},{\"id\":4,\"name\":\"Roswelt\",\"city\":\"Pittsburgh\"}]";

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void getRanchByNameNotFoundException() throws Exception{

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get(PATH_RANCHES_WRONG_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

    }

    @Test
    void addRanch() throws Exception {

        Ranch mockResponse = Ranch.builder()
                .id(7)
                .name("Mully")
                .city("Pittsburgh")
                .build();

        Mockito.when(ranchService.addRanch(ArgumentMatchers.any(Ranch.class))).thenReturn(mockResponse);

        String bodyRequest = "{\"name\":\"Mully\",\"city\":\"Pittsburgh\"}";

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(PATH_RANCHES)
                .content(bodyRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = "{\"id\":7,\"name\":\"Mully\",\"city\":\"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    void addRanchBadRequestException() throws Exception {

        Ranch mockResponse = Ranch.builder()
                                  .id(7)
                                  .name("Mully")
                                  .build();

        String bodyRequest = "{\"name\":\"Mully\"}";

        Mockito.when(ranchService.addRanch(ArgumentMatchers.any(Ranch.class))).thenReturn(mockResponse);

        mvc.perform(
                MockMvcRequestBuilders.post(PATH_RANCHES)
                .content(bodyRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    List<Ranch> initRanches() {
        return Arrays.asList(
                Ranch.builder().id(1).name("Riac").city("Pittsburgh").build(),
                Ranch.builder().id(2).name("Bates").city("California").build(),
                Ranch.builder().id(3).name("Pa").city("Michigan").build(),
                Ranch.builder().id(4).name("Roswelt").city("Pittsburgh").build(),
                Ranch.builder().id(5).name("Rasson").city("New York").build(),
                Ranch.builder().id(6).name("Miou").city("Houston").build()
        );
    }

    List<Ranch> initPartialRanches() {
        return Arrays.asList(
                Ranch.builder().id(1).name("Riac").city("Pittsburgh").build(),
                Ranch.builder().id(4).name("Roswelt").city("Pittsburgh").build()
        );
    }

    Ranch initRanchResponse() {

        return Ranch.builder()
                .id(1)
                .name("Riac")
                .city("Pittsburgh")
                .build();
    }


}
