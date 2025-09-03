package tech.techsete.sync_pay_sdk.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    FAILED("failed"),
    REFUNDED("refunded"),
    MED("med");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }
}
