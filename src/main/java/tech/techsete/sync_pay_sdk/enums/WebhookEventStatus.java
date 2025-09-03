package tech.techsete.sync_pay_sdk.enums;

import lombok.Getter;

@Getter
public enum WebhookEventStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    FAILED("failed"),
    REFUNDED("refunded"),
    MED("med");

    private final String value;

    WebhookEventStatus(String value) {
        this.value = value;
    }
}
