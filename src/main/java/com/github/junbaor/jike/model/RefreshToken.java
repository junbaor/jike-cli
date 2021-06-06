package com.github.junbaor.jike.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RefreshToken {

    @JsonProperty("x-jike-access-token")
    private String accessToken;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("x-jike-refresh-token")
    private String refreshToken;

}
