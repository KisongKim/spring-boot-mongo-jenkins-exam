package com.archtiger.exam.web;

import com.archtiger.exam.ExamApplication;
import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExamApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:before_integration_tests.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:after_integration_tests.sql")
})
public class GameInventoryControllerIntegrationTests {

    // To test HTTP 'PATCH' we have to use RestTemplate
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static MultiValueMap<String, String> headers() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    @Autowired
    public GameInventoryControllerIntegrationTests() {
        // To test 'PATCH' we have to use apache http client
        restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Test
    public void updateInventoryStockCount_expect200() {
        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(10, 5);
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers());

        ResponseEntity<UpdateProductStockCountResponse> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/exam/inventory/{id}",
                HttpMethod.PATCH,
                requestEntity,
                UpdateProductStockCountResponse.class,
                1);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void updateInventoryStockCount_expect400_causeNumberFormatException() {
        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(10, 5);
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers());

        try {
            restTemplate.exchange(
                    "http://localhost:" + port + "/exam/inventory/{id}",
                    HttpMethod.PATCH,
                    requestEntity,
                    UpdateProductStockCountResponse.class,
                    "ABC");
        } catch (HttpStatusCodeException e) {
            Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(e.getResponseBodyAsString()).containsSequence("NumberFormatException");
            System.out.println(e.getResponseBodyAsString());
        }
    }

    @Test
    public void updateInventoryStockCount_expect400_causeValidationFailedForArgument() {
        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(10, -1);
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers());
        try {
            restTemplate.exchange(
                    "http://localhost:" + port + "/exam/inventory/{id}",
                    HttpMethod.PATCH,
                    requestEntity,
                    UpdateProductStockCountResponse.class,
                    "1");
        } catch (HttpStatusCodeException e) {
            Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(e.getResponseBodyAsString()).containsSequence("Validation failed for argument");
            System.out.println(e.getResponseBodyAsString());
        }
    }


}
