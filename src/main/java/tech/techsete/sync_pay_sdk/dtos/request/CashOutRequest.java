package tech.techsete.sync_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.techsete.sync_pay_sdk.enums.DocumentType;
import tech.techsete.sync_pay_sdk.enums.PixKeyType;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CashOutRequest implements Serializable {

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @NotNull(message = "Pix key type cannot be null")
    @JsonProperty("pix_key_type")
    private PixKeyType pixKeyType;

    @NotEmpty(message = "Pix key cannot be empty")
    @JsonProperty("pix_key")
    private String pixKey;

    @NotNull(message = "Document cannot be null")
    @JsonProperty("document")
    private Document document;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {

        @NotNull(message = "Document type cannot be null")
        @JsonProperty("type")
        private DocumentType type;

        @NotEmpty(message = "Document number cannot be empty")
        @JsonProperty("number")
        private String number;
    }
}
