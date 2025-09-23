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

        @JsonProperty("amount")
        private Integer amount;

        @JsonProperty("status")
        private WebhookEventStatus status;

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("checkout")
        private Checkout checkout;

        @JsonProperty("client_name")
        private String clientName;

        @JsonProperty("paymentcode")
        private String paymentCode;

        @JsonProperty("client_email")
        private String clientEmail;

        @JsonProperty("data_registro")
        @JsonDeserialize(using = MultiFormatDateDeserializer.class)
        private OffsetDateTime dataRegistro;

        @JsonProperty("idtransaction")
        private String idTransaction;

        @JsonProperty("taxa_deposito")
        private Double taxaDeposito;

        @JsonProperty("adquirente_ref")
        private String adquirenteRef;

        @JsonProperty("client_document")
        private String clientDocument;

        @JsonProperty("taxa_adquirente")
        private Double taxaAdquirente;

        @JsonProperty("deposito_liquido")
        private Double depositoLiquido;

        @JsonProperty("externalreference")
        private String externalReference;

        @JsonProperty("paymentCodeBase64")
        private String paymentCodeBase64;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        private static class Checkout implements Serializable {

            @JsonProperty("utm_term")
            private String utmTerm;

            @JsonProperty("utm_medium")
            private String utmMedium;

            @JsonProperty("utm_source")
            private String utmSource;

            @JsonProperty("utm_content")
            private String utmContent;

            @JsonProperty("utm_campaign")
            private String utmCampaign;
        }
    }
}