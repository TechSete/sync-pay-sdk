package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.dtos.response.TransactionResponse;

import java.util.Map;

@Service("syncPayTransactionService")
public class TransactionService {

    private final String baseURL = "/api/partner/v1/transaction";
    private final WebClient webClient;

    public TransactionService(@Qualifier("syncPayWebClient")
                              WebClient webClient
    ) {
        this.webClient = webClient;
    }

    public TransactionResponse findTransaction(Map<String, ?> headers, String id) {
        return findTransactionAsync(headers, id).block();
    }

    public Mono<TransactionResponse> findTransactionAsync(Map<String, ?> headers, String id) {
        return webClient.get()
                .uri(String.format("%s/%s", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(TransactionResponse.class);
    }
}