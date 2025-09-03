package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.dtos.request.CashInRequest;
import tech.techsete.sync_pay_sdk.dtos.request.CashOutRequest;
import tech.techsete.sync_pay_sdk.dtos.response.CashInResponse;
import tech.techsete.sync_pay_sdk.dtos.response.CashOutResponse;
import tech.techsete.sync_pay_sdk.exceptions.InvalidCashInRequestException;

import java.util.Map;

@Service(value = "syncPayPixService")
public class PixService {

    private final WebClient webClient;

    public PixService(@Qualifier("syncPayWebClient")
                      WebClient webClient
    ) {
        this.webClient = webClient;
    }

    public CashInResponse createCashIn(Map<String, ?> headers, CashInRequest cashInRequest) {
        validateCashInRequest(cashInRequest);
        return createCashInAsync(headers, cashInRequest).block();
    }

    public Mono<CashInResponse> createCashInAsync(Map<String, ?> headers, CashInRequest cashInRequest) {
        validateCashInRequest(cashInRequest);
        return webClient.post()
                .uri("/api/partner/v1/cash-in")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(cashInRequest)
                .retrieve()
                .bodyToMono(CashInResponse.class);
    }

    public CashOutResponse createCashOut(Map<String, ?> headers, CashOutRequest cashOutRequest) {
        return createCashOutAsync(headers, cashOutRequest).block();
    }

    public Mono<CashOutResponse> createCashOutAsync(Map<String, ?> headers, CashOutRequest cashOutRequest) {
        return webClient.post()
                .uri("/api/partner/v1/cash-out")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(cashOutRequest)
                .retrieve()
                .bodyToMono(CashOutResponse.class);
    }

    private void validateCashInRequest(CashInRequest cashInRequest) {

        if (cashInRequest == null) {
            throw new InvalidCashInRequestException("Cash in request cannot be null");
        }

        if (cashInRequest.getAmount() == null) {
            throw new InvalidCashInRequestException("Cash in amount cannot be null");
        }

        if (cashInRequest.getAmount().compareTo(new java.math.BigDecimal("0.00")) < 0) {
            throw new InvalidCashInRequestException("Cash in amount cannot be negative");
        }

        if (cashInRequest.getSplit() != null && !cashInRequest.getSplit().isEmpty()) {
            int sumSplitPercentage = 0;

            for (var split : cashInRequest.getSplit()) {
                if (split.getPercentage() == null || split.getPercentage() < 0 || split.getPercentage() > 100) {
                    throw new InvalidCashInRequestException("Split percentage must be between 0 and 100");
                }

                if (split.getUserId() == null || split.getUserId().isBlank()) {
                    throw new InvalidCashInRequestException("Split user id cannot be blank");
                }

                sumSplitPercentage += split.getPercentage();
            }

            if (sumSplitPercentage > 100) {
                throw new InvalidCashInRequestException("Sum of split percentage cannot be greater than 100");
            }
        }
    }
}
