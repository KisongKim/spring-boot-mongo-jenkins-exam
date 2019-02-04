package com.archtiger.exam.web;

import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.service.CustomerRegisterService;
import com.archtiger.exam.service.CustomerSearchService;
import com.archtiger.exam.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerWebMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRegisterService customerRegisterService;

    @MockBean
    private CustomerSearchService customerSearchService;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerRegisterService,
                customerSearchService,
                customerService)).build();
    }

    public static CustomerRegisterRequest customerRegisterRequest(String email,
                                                                  String password,
                                                                  String familyName,
                                                                  String givenName) {
        return new CustomerRegisterRequest(email, password, familyName, givenName);
    }

    @Test
    public void register() throws Exception {
        final String email = "tom@test.com";
        final String password = "secret";
        final String familyName = "tom";
        final String givenName = "martin";

        CustomerRegisterRequest request = customerRegisterRequest(email, password, familyName, givenName);

        CustomerRegisterResponse response = new CustomerRegisterResponse(email,
                familyName, givenName, LocalDateTime.now());
        Mockito.when(customerRegisterService.register(Mockito.any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/customer")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ControllerTestUtils.asJsonString(request));
        mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("tom@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familyName", Matchers.is("tom")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.givenName", Matchers.is("martin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registerDateTime").exists());
    }

}
