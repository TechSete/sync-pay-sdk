package tech.techsete.sync_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.techsete.sync_pay_sdk.deserializers.MultiFormatDateDeserializer;
import tech.techsete.sync_pay_sdk.enums.WebhookEventStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebhookEventRequest implements Serializable {

    @JsonProperty("data")
    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        @JsonProperty("id")
        private String id;

        @JsonProperty("client")
        private Client client;

        @JsonProperty("pix_key")
        private String pixCode;

        @JsonProperty("amount")
        private Integer amount;

        @JsonProperty("final_amount")
        private Integer finalAmount;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("status")
        private WebhookEventStatus status;

        @JsonProperty("payment_method")
        private String paymentMethod;

        @JsonProperty("created_at")
        @JsonDeserialize(using = MultiFormatDateDeserializer.class)
        private OffsetDateTime createdAt;

        @JsonProperty("updated_at")
        @JsonDeserialize(using = MultiFormatDateDeserializer.class)
        private OffsetDateTime updatedAt;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Client {

            @JsonProperty("name")
            private String name;

            @JsonProperty("email")
            private String email;

            @JsonProperty("document")
            private String document;
        }
    }
}