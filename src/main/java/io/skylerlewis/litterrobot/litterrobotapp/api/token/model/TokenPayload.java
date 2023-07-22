package io.skylerlewis.litterrobot.litterrobotapp.api.token.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@ToString
@Data
public class TokenPayload {

    private String userId;
    private String clientId;
    @JsonProperty("iat")
    private Instant issuedAt;
    @JsonProperty("exp")
    private Instant expiresAt;

}
