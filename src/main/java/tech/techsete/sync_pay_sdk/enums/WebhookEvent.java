package tech.techsete.sync_pay_sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum WebhookEvent {
    CASH_IN("cashin"),
    CASH_OUT("cashout"),
    INFRACTION("infraction");

    private final String value;

    WebhookEvent(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WebhookEvent fromValue(String value) {
        for (WebhookEvent event : WebhookEvent.values()) {
            if (event.value.equalsIgnoreCase(value)) {
                return event;
            }
        }
        throw new IllegalArgumentException("Evento inválido: " + value);
    }
}