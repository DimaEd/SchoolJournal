package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for generated token
 */
@Getter
@Setter
public class TokenResponseDTO {

    private String token;

    private String type = "Bearer";

    public TokenResponseDTO(String accessToken) {
        this.token = accessToken;
    }
}
