package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CashOutResponse(

        @JsonProperty("message")
        String message,

        @JsonProperty("reference_id")
        String referenceId
) implements Serializable { }