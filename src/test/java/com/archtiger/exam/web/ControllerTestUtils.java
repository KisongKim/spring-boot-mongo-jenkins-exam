package com.archtiger.exam.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ControllerTestUtils {

    public static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MultiValueMap<String, String> headers(boolean content) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        if (content == true) {
            headers.add("Content-Type", "application/json");
        }
        return headers;
    }

}
