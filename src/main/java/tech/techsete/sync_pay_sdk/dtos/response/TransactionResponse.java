package tech.techsete.sync_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.techsete.sync_pay_sdk.enums.Currency;
import tech.techsete.sync_pay_sdk.enums.TransactionStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse implements Serializable {

    @JsonProperty("data")
    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        @JsonProperty("reference_id")
        private String referenceId;

        @JsonProperty("currency")
        private Currency currency;

        @JsonProperty("amount")
        private Double amount;

        @JsonProperty("transaction_date")
        private OffsetDateTime transactionDate;

        @JsonProperty("status")
        private TransactionStatus status;

        @JsonProperty("description")
        private String description;

        @JsonProperty("pix_code")
        private String pixCode;
    }
}