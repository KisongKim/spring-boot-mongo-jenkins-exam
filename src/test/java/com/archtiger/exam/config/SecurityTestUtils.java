package com.archtiger.exam.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityTestUtils {

    private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Test
    public void passwordEncode_bar() {
        PasswordEncoder encoder = passwordEncoder();
        String encoded = encoder.encode("bar");
        Assert.assertNotNull(encoded);
        System.out.println(encoded);
    }

    @Test
    public void passwordEncode_password() {
        PasswordEncoder encoder = passwordEncoder();
        String encoded = encoder.encode("password");
        Assert.assertNotNull(encoded);
        System.out.println(encoded);
    }

}
