package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CashInResponse(

        @JsonProperty("message")
        String message,

        @JsonProperty("pix_code")
        String pixCode,

        @JsonProperty("identifier")
        String identifier
) implements Serializable { }
