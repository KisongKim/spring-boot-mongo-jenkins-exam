package com.archtiger.exam.web;

import com.archtiger.exam.ExamApplication;
import com.archtiger.exam.contract.ListingGamesResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExamApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:before_integration_tests.sql")
})
public class GameControllerIntegrationTests {

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    public GameControllerIntegrationTests() {
        //this.testRestTemplate = new TestRestTemplate("user", "secret");
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void listingGames_byPlatform_should200() {
        ResponseEntity<ListingGamesResponse> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/exam/games?platform={platform}",
                HttpMethod.GET,
                null,
                ListingGamesResponse.class,
                "PS_4");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ListingGamesResponse response = responseEntity.getBody();
        Assertions.assertThat(response.getGameInformations().size()).isEqualTo(2);
    }

}
