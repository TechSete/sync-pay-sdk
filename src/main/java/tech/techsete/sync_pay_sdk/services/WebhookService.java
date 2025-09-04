package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.domain.Page;
import tech.techsete.sync_pay_sdk.dtos.request.WebhookRequest;
import tech.techsete.sync_pay_sdk.dtos.response.WebhookResponse;

import java.util.Map;

@Service("syncPayWebhookService")
public class WebhookService {

    private final String baseURL = "/api/partner/v1/webhooks";
    private final WebClient webClient;

    public WebhookService(@Qualifier("syncPayWebClient")
                          WebClient webClient
    ) {
        this.webClient = webClient;
    }

    public WebhookResponse createWebhook(Map<String, ?> headers, WebhookRequest webhookRequest) {
        return createWebhookAsync(headers, webhookRequest).block();
    }

    public Mono<WebhookResponse> createWebhookAsync(Map<String, ?> headers, WebhookRequest webhookRequest) {
        return webClient.post()
                .uri(baseURL)
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(webhookRequest)
                .retrieve()
                .bodyToMono(WebhookResponse.class);
    }

    public String deleteWebhook(Map<String, ?> headers, Integer id) {
        return deleteWebhookAsync(headers, id).block();
    }

    public Mono<String> deleteWebhookAsync(Map<String, ?> headers, Integer id) {
        return webClient.delete()
                .uri(String.format("%s/%d", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Page<WebhookResponse> findAllWebhooks(Map<String, ?> headers) {
        return findAllWebhooksAsync(headers, null).block();
    }

    public Page<WebhookResponse> findAllWebhooks(Map<String, ?> headers, Map<String, ?> queryParams) {
        return findAllWebhooksAsync(headers, queryParams).block();
    }

    public Mono<Page<WebhookResponse>> findAllWebhooksAsync(Map<String, ?> headers, Map<String, ?> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(baseURL);
                    queryParams.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<WebhookResponse>>() {});
    }

    public WebhookResponse updateWebhook(Map<String, ?> headers, Integer id, WebhookRequest webhookRequest) {
        return updateWebhookAsync(headers, id, webhookRequest).block();
    }

    public Mono<WebhookResponse> updateWebhookAsync(Map<String, ?> headers, Integer id, WebhookRequest webhookRequest) {
        return webClient.put()
                .uri(String.format("%s/%d", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(webhookRequest)
                .retrieve()
                .bodyToMono(WebhookResponse.class);
    }
}
