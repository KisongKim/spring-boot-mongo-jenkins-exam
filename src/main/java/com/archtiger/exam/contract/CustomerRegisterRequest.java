package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
public class CustomerRegisterRequest {

    @Email(message = "Must valid email format.")
    @Size(max = 256, message = "Email length should be less then 256")
    @JsonProperty(required = true)
    private String email;

    @NotEmpty(message = "Password must be provided")
    @JsonProperty(required = true)
    private String password;

    @Size(min = 1, max = 64, message = "Family name length must be 1-64")
    @JsonProperty(required = true)
    private String familyName;

    @Size(min = 1, max = 64, message = "Family name length must be 1-64")
    @JsonProperty(required = true)
    private String givenName;

}
