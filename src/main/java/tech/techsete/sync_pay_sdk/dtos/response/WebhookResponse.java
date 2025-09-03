package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.techsete.sync_pay_sdk.enums.WebhookEvent;

import java.io.Serializable;
import java.time.LocalDateTime;

public record WebhookResponse(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("title")
        String title,

        @JsonProperty("url")
        String url,

        @JsonProperty("event")
        WebhookEvent event,

        @JsonProperty("trigger_all_products")
        boolean triggerAllProducts,

        @JsonProperty("token")
        String token,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) implements Serializable { }