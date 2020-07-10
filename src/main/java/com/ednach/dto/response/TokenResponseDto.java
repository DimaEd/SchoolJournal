package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for generated token
 */
@Getter
@Setter
public class TokenResponseDto {

    private String token;

    private String type = "Bearer";

    public TokenResponseDto(String accessToken) {
        this.token = accessToken;
    }
}
