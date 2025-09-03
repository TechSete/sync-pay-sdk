package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record AuthenticationResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("expires_at")
        OffsetDateTime expiresAt
) implements Serializable { }