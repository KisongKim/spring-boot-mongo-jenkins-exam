package com.archtiger.exam.web;

import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;
import com.archtiger.exam.service.GameInventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@WebMvcTest(GameInventoryController.class)
public class GameInventoryControllerWebMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameInventoryService gameInventoryService;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new GameInventoryController(gameInventoryService)).build();
    }

    @Test
    public void updateInventoryStockCount() throws Exception {
        UpdateProductStockCountResponse response = new UpdateProductStockCountResponse(1, 1);
        Mockito.when(gameInventoryService.updateStockCount(Mockito.anyLong(), Mockito.any())).thenReturn(response);

        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(5, 1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/inventory/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(request));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockCount", Matchers.is(1)));
    }

    @Test
    public void updateInventoryStockCount_expectStatusCode400() throws Exception {
        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(5, 1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/inventory/{id}", "abc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(request));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateInventoryStockCount_expectStatusCode400_causeValidationFail() throws Exception {
        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/inventory/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(request));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void idPattern() {
        int id = 10;
        Pattern idPattern = Pattern.compile("^[1-9][0-9]{0,4}$");
        Matcher matcher = idPattern.matcher(String.valueOf(id));
        Assert.assertTrue(matcher.matches());
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
