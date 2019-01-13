package com.archtiger.exam.web;

import com.archtiger.exam.ExamApplication;
import com.archtiger.exam.contract.ListingGamesResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIntegrationTests {

    /*
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void listingGames() {
        // setup request parameter
        Map<String, String> param = new HashMap<>();
        param.put("platform", "PS_4");

        // do test
        ResponseEntity<ListingGamesResponse> responseEntity = testRestTemplate.exchange("/games",
                HttpMethod.GET,
                null,
                ListingGamesResponse.class,
                param);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    */
}
