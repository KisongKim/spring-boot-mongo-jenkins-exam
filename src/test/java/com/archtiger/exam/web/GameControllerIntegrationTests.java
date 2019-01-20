package com.archtiger.exam.web;

import com.archtiger.exam.ExamApplication;
import com.archtiger.exam.contract.ExamErrorResponse;
import com.archtiger.exam.contract.PagedGamesResponse;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExamApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:before_integration_tests.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:after_integration_tests.sql")
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
    public void getGames_expected200() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start = LocalDate.now().minusYears(1).format(formatter);
        String end = LocalDate.now().plusMonths(1).format(formatter);

        ResponseEntity<PagedGamesResponse> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/exam/games?start={s}&end={e}&page={p}&limit={l}&sort={sort}",
                HttpMethod.GET,
                null,
                PagedGamesResponse.class,
                start,
                end,
                0,
                50,
                "desc");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void getGames_invalidDateFormatExpected400() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String end = LocalDate.now().plusMonths(1).format(formatter);
        ResponseEntity<ExamErrorResponse> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/exam/games?start={s}&end={e}&page={p}&limit={l}&sort={sort}",
                HttpMethod.GET,
                null,
                ExamErrorResponse.class,
                "20181231",
                end,
                0,
                50,
                "desc");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        System.out.println(responseEntity.getBody().toString());
    }

}
