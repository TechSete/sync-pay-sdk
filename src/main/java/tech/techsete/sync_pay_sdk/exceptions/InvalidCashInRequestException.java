package tech.techsete.sync_pay_sdk.exceptions;

public class InvalidCashInRequestException extends RuntimeException {
    public InvalidCashInRequestException(String message) {
        super(message);
    }
}
