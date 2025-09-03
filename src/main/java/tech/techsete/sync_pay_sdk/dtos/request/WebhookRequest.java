package tech.techsete.sync_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.techsete.sync_pay_sdk.enums.WebhookEvent;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebhookRequest implements Serializable {

    @NotEmpty(message = "Title cannot be empty")
    @JsonProperty("title")
    private String title;

    @NotEmpty(message = "URL cannot be empty")
    @JsonProperty("url")
    private String url;

    @NotNull(message = "Event cannot be null")
    @JsonProperty("event")
    private WebhookEvent event;

    @NotNull(message = "Trigger all products cannot be null")
    @JsonProperty("trigger_all_products")
    private boolean triggerAllProducts;
}
