package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import tech.techsete.sync_pay_sdk.deserializers.MultiFormatDateDeserializer;
import tech.techsete.sync_pay_sdk.enums.WebhookEvent;

import java.io.Serializable;
import java.time.OffsetDateTime;

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
        @JsonDeserialize(using = MultiFormatDateDeserializer.class)
        OffsetDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonDeserialize(using = MultiFormatDateDeserializer.class)
        OffsetDateTime updatedAt
) implements Serializable {
}