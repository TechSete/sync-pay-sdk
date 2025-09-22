package tech.techsete.sync_pay_sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum WebhookEventStatus {
    COMPLETED("completed"),
    FAILED("failed"),
    MED("med"),
    PAID_OUT("paid_out"),
    PENDING("pending"),
    REFUNDED("refunded"),
    WAITING_FOR_APPROVAL("waiting_for_approval");

    private final String value;

    WebhookEventStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WebhookEventStatus fromValue(String value) {
        for (WebhookEventStatus status : WebhookEventStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
