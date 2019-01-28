package com.archtiger.exam.web;

import com.archtiger.exam.ExamApplication;
import com.archtiger.exam.ExamError;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.contract.ExamErrorResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
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
                scripts = "classpath:before_integration_tests.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:after_integration_tests.sql")
})
public class CustomerControllerIntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private static CustomerRegisterRequest customerRegisterRequest(String email,
                                                                   String password,
                                                                   String familyName,
                                                                   String givenName) {
        return new CustomerRegisterRequest(email, password, familyName, givenName);
    }

    @Test
    public void register_expect201() {
        final String email = "michael@test.com";
        final String password = "password";
        final String familyName = "michael";
        final String givenName = "jackson";
        CustomerRegisterRequest request = customerRegisterRequest(email, password, familyName, givenName);

        HttpEntity<Object> requestEntity = new HttpEntity<>(request,
                ControllerTestUtils.headers(true));

        String url = "http://localhost:" + port + "/exam/customer";
        ResponseEntity<CustomerRegisterResponse> responseEntity = testRestTemplate.exchange(url,
                HttpMethod.POST,
                requestEntity,
                CustomerRegisterResponse.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        CustomerRegisterResponse errorResponse = responseEntity.getBody();
        Assertions.assertThat(errorResponse.getEmail()).isEqualTo(email);
        Assertions.assertThat(errorResponse.getFamilyName()).isEqualTo(familyName);
        Assertions.assertThat(errorResponse.getGivenName()).isEqualTo(givenName);
    }

    @Test
    public void register_expect400_emailAlreadyExist() {
        final String email = "bruce@test.com";
        final String password = "password";
        final String familyName = "bruce";
        final String givenName = "wayne";
        CustomerRegisterRequest request = customerRegisterRequest(email, password, familyName, givenName);

        HttpEntity<Object> requestEntity = new HttpEntity<>(request,
                ControllerTestUtils.headers(true));

        String url = "http://localhost:" + port + "/exam/customer";
        ResponseEntity<ExamErrorResponse> responseEntity = testRestTemplate.exchange(url,
                HttpMethod.POST,
                requestEntity,
                ExamErrorResponse.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ExamErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertThat(errorResponse.getCode()).isEqualTo(ExamError.EMAIL_ALREADY_EXIST.getCode());
    }

    @Test
    public void register_expect400_password() {
        final String email = "bruce@test.com";
        final String familyName = "bruce";
        final String givenName = "wayne";
        CustomerRegisterRequest request = customerRegisterRequest(email, null, familyName, givenName);

        HttpEntity<Object> requestEntity = new HttpEntity<>(request,
                ControllerTestUtils.headers(true));

        String url = "http://localhost:" + port + "/exam/customer";
        ResponseEntity<ExamErrorResponse> responseEntity = testRestTemplate.exchange(url,
                HttpMethod.POST,
                requestEntity,
                ExamErrorResponse.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ExamErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertThat(errorResponse.getCode()).isEqualTo(40000);
    }

}
