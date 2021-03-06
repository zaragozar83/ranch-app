package com.riac.teapcount.ranchapp.integrationtesting.controller;

import com.riac.teapcount.ranchapp.domain.model.Ranch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RanchControllerTest {

    @LocalServerPort
    private int PORT;

    private final String HOST_PATH = "http://localhost:";
    private final String PATH_RANCHES = "/ranches";
    private final String PATH_RANCHES_ID = "/ranches/1";
    private final String PATH_RANCHES_NAME = "/ranches/Riac/name";
    private final String PATH_RANCHES_WRONG_NAME = "/ranches/10";
    private final String PATH_RANCHES_CITY = "/ranches/Pittsburgh/city";

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void getRanchById() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
                                                                    createURL(PATH_RANCHES_ID),
                                                                    String.class);

        String expected = "{\"id\": 1,\"name\": \"Riac\",\"city\": \"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, response.getBody().toString(), false);

    }

    @Test
    void getRanchByName() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURL(PATH_RANCHES_NAME),
                String.class);

        String expected = "{\"id\": 1,\"name\": \"Riac\",\"city\": \"Pittsburgh\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    void getRanchesByCity() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURL(PATH_RANCHES_CITY),
                String.class
        );

        String expected = "[{\"id\":1,\"name\":\"Riac\",\"city\":\"Pittsburgh\"},{\"id\":4,\"name\":\"Roswelt\",\"city\":\"Pittsburgh\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void addRanch() throws Exception {

        Ranch ranchBody = Ranch.builder()
                                .name("Mully")
                                .city("Pachuca")
                                .build();

        ResponseEntity<Ranch> response = restTemplate.postForEntity(
                                                                    createURL(PATH_RANCHES),
                                                                    ranchBody,
                                                                    Ranch.class
                                                                    );

        Ranch expectedRanch = Ranch.builder()
                                    .id(7)
                                    .name("Mully")
                                    .city("Pachuca")
                                    .build();

        Assertions.assertEquals(expectedRanch, response.getBody());

    }

    @Test
    void addRanchBadRequest() throws Exception {

        Ranch ranchBody = Ranch.builder()
                .name("Mully")
                .build();

        ResponseEntity<Ranch> response = restTemplate.postForEntity(
                createURL(PATH_RANCHES),
                ranchBody,
                Ranch.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void getRanchByIdNotFoundException() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(createURL(PATH_RANCHES_WRONG_NAME),
                                                                            String.class);

        System.out.println(response);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("The Ranch: 10 is not found"));
    }

    private String createURL(String path) {
        return HOST_PATH + PORT + path;
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
