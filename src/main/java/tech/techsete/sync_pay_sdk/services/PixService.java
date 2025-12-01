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

/**
 * Serviço responsável pelas operações de PIX, incluindo Cash-In e Cash-Out.
 * <p>
 * Esta classe realiza chamadas à API SyncPay para gerenciar operações financeiras de entrada (Cash-In)
 * e saída (Cash-Out) de valores utilizando o sistema de pagamentos instantâneos (PIX).
 * Possui métodos para operações síncronas e assíncronas, além de validações nos dados de entrada.
 * </p>
 *
 * <p><b>Endpoints utilizados:</b></p>
 * <ul>
 *     <li><b>URI Cash-In:</b> {@code /api/partner/v1/cash-in}</li>
 *     <li><b>URI Cash-Out:</b> {@code /api/partner/v1/cash-out}</li>
 * </ul>
 *
 * @see WebClient
 * @see tech.techsete.sync_pay_sdk.dtos.request.CashInRequest
 * @see tech.techsete.sync_pay_sdk.dtos.response.CashInResponse
 * @see tech.techsete.sync_pay_sdk.dtos.request.CashOutRequest
 * @see tech.techsete.sync_pay_sdk.dtos.response.CashOutResponse
 * @see tech.techsete.sync_pay_sdk.exceptions.InvalidCashInRequestException
 *
 * @author Edson Isaac
 */

@Service(value = "syncPayPixService")
public class PixService {

    private final WebClient webClient;

    /**
     * Construtor responsável por inicializar o cliente HTTP {@link WebClient} necessário
     * para a comunicação com a API SyncPay.
     *
     * @param webClient O cliente HTTP configurado para integração com os endpoints da API SyncPay.
     */

    public PixService(@Qualifier("syncPayWebClient")
                      WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Realiza operação de entrada de valores (Cash-In) de forma síncrona.
     * <p>
     * Este método valida a requisição antes de realizar a chamada ao endpoint de Cash-In.
     * </p>
     *
     * @param headers          Cabeçalhos HTTP a serem enviados na requisição.
     * @param cashInRequest    Dados da requisição de entrada de valores.
     * @return {@link CashInResponse} com os detalhes do Cash-In realizado.
     * @throws InvalidCashInRequestException Se os dados de entrada forem inválidos.
     */
    public CashInResponse createCashIn(Map<String, ?> headers, CashInRequest cashInRequest) {
        validateCashInRequest(cashInRequest);
        return createCashInAsync(headers, cashInRequest).block();
    }

    /**
     * Realiza operação de entrada de valores (Cash-In) de forma assíncrona.
     * <p>
     * Este método valida a requisição antes de realizar a chamada ao endpoint de Cash-In.
     * </p>
     *
     * @param headers          Cabeçalhos HTTP a serem enviados na requisição.
     * @param cashInRequest    Dados da requisição de entrada de valores.
     * @return {@link Mono} contendo {@link CashInResponse} com os detalhes do Cash-In realizado.
     * @throws InvalidCashInRequestException Se os dados de entrada forem inválidos.
     */
    public Mono<CashInResponse> createCashInAsync(Map<String, ?> headers, CashInRequest cashInRequest) {
        validateCashInRequest(cashInRequest);
        return webClient.post()
                .uri("/api/partner/v1/cash-in")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(cashInRequest)
                .retrieve()
                .bodyToMono(CashInResponse.class);
    }

    /**
     * Realiza operação de saída de valores (Cash-Out) de forma síncrona.
     *
     * @param headers          Cabeçalhos HTTP a serem enviados na requisição.
     * @param cashOutRequest   Dados da requisição de saída de valores.
     * @return {@link CashOutResponse} com os detalhes do Cash-Out realizado.
     */
    public CashOutResponse createCashOut(Map<String, ?> headers, CashOutRequest cashOutRequest) {
        return createCashOutAsync(headers, cashOutRequest).block();
    }

    /**
     * Realiza operação de saída de valores (Cash-Out) de forma assíncrona.
     *
     * @param headers          Cabeçalhos HTTP a serem enviados na requisição.
     * @param cashOutRequest   Dados da requisição de saída de valores.
     * @return {@link Mono} contendo {@link CashOutResponse} com os detalhes do Cash-Out realizado.
     */
    public Mono<CashOutResponse> createCashOutAsync(Map<String, ?> headers, CashOutRequest cashOutRequest) {
        return webClient.post()
                .uri("/api/partner/v1/cash-out")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(cashOutRequest)
                .retrieve()
                .bodyToMono(CashOutResponse.class);
    }

    /**
     * Valida os dados de uma requisição de entrada de valores (Cash-In).
     * <p>
     * Este método verifica se os dados fornecidos no {@link CashInRequest} estão consistentes,
     * incluindo regras como:
     * <ul>
     *     <li>Requisição não pode ser nula</li>
     *     <li>Valor (amount) não pode ser nulo ou negativo</li>
     *     <li>Percentuais nos splits devem ser válidos (entre 0 e 100) e a soma não pode exceder 100%</li>
     * </ul>
     * Caso alguma validação falhe, uma {@link InvalidCashInRequestException} é lançada.
     * </p>
     *
     * @param cashInRequest Dados da requisição de Cash-In a serem validados.
     * @throws InvalidCashInRequestException Caso os dados da requisição sejam inválidos.
     */
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
