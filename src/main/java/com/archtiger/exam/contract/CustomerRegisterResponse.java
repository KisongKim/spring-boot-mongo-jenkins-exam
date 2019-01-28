package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
public class CustomerRegisterResponse {

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private String familyName;

    @JsonProperty(required = true)
    private String givenName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH::mm:ss", timezone = "Europe/Berlin")
    @JsonProperty(required = true)
    private LocalDateTime registerDateTime;

}
