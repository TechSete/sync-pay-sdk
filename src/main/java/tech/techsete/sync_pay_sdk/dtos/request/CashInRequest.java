package tech.techsete.sync_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CashInRequest implements Serializable {

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("webhook_url")
    private String webhookUrl;

    @JsonProperty("client")
    private Client client;

    @JsonProperty("split")
    private List<Split> split;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client {

        @NotEmpty(message = "Client name cannot be empty")
        @JsonProperty("name")
        private String name;

        @CPF(message = "Client cpf must be a valid CPF")
        @NotEmpty(message = "Client cpf cannot be empty")
        @JsonProperty("cpf")
        private String cpf;

        @Email(message = "Client email must be a valid email")
        @NotEmpty(message = "Client email cannot be empty")
        @JsonProperty("email")
        private String email;

        @Pattern(
                regexp = "^(\\+\\d{10,15}|\\d{10,11})$",
                message = "Client phone must be in E.164 format or a valid Brazilian number with DDD"
        )
        @NotEmpty(message = "Client phone cannot be empty")
        @JsonProperty("phone")
        private String phone;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Split {

        @NotNull(message = "Split percentage cannot be null")
        @JsonProperty("percentage")
        private Integer percentage;

        @NotEmpty(message = "Split user id cannot be empty")
        @JsonProperty("user_id")
        private String userId;
    }
}