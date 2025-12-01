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

/**
 * Serviço responsável pela gestão de Webhooks na API SyncPay.
 * <p>
 * Esta classe realiza chamadas à API SyncPay para criar, listar, atualizar e deletar webhooks.
 * Permite a interação com os endpoints de webhooks de forma síncrona e assíncrona.
 * </p>
 *
 * <p><b>Endpoints utilizados:</b></p>
 * <ul>
 *     <li><b>URI Base:</b> {@code /api/partner/v1/webhooks}</li>
 * </ul>
 *
 * @see WebClient
 * @see tech.techsete.sync_pay_sdk.dtos.request.WebhookRequest
 * @see tech.techsete.sync_pay_sdk.dtos.response.WebhookResponse
 * @see tech.techsete.sync_pay_sdk.domain.Page
 */

@Service("syncPayWebhookService")
public class WebhookService {

    private final String baseURL = "/api/partner/v1/webhooks";
    private final WebClient webClient;

    /**
     * Construtor responsável por configurar o cliente HTTP {@link WebClient}.
     *
     * @param webClient O cliente HTTP configurado para comunicação com a API SyncPay, providenciado pelo contexto Spring.
     */
    public WebhookService(@Qualifier("syncPayWebClient")
                          WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Cria um webhook de forma síncrona.
     *
     * @param headers         Cabeçalhos HTTP enviados na requisição.
     * @param webhookRequest  Dados do webhook a ser criado.
     * @return {@link WebhookResponse} contendo os detalhes do webhook criado.
     */
    public WebhookResponse createWebhook(Map<String, ?> headers, WebhookRequest webhookRequest) {
        return createWebhookAsync(headers, webhookRequest).block();
    }

    /**
     * Cria um webhook de forma assíncrona.
     *
     * @param headers         Cabeçalhos HTTP enviados na requisição.
     * @param webhookRequest  Dados do webhook a ser criado.
     * @return {@link Mono} contendo {@link WebhookResponse} com os detalhes do webhook criado.
     */
    public Mono<WebhookResponse> createWebhookAsync(Map<String, ?> headers, WebhookRequest webhookRequest) {
        return webClient.post()
                .uri(baseURL)
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(webhookRequest)
                .retrieve()
                .bodyToMono(WebhookResponse.class);
    }

    /**
     * Deleta um webhook de forma síncrona.
     *
     * @param headers Cabeçalhos HTTP enviados na requisição.
     * @param id      Identificador único do webhook a ser deletado.
     * @return {@code String} informando o resultado da operação de deleção.
     */
    public String deleteWebhook(Map<String, ?> headers, Integer id) {
        return deleteWebhookAsync(headers, id).block();
    }

    /**
     * Deleta um webhook de forma assíncrona.
     *
     * @param headers Cabeçalhos HTTP enviados na requisição.
     * @param id      Identificador único do webhook a ser deletado.
     * @return {@link Mono} contendo uma {@code String} com o resultado da operação de deleção.
     */
    public Mono<String> deleteWebhookAsync(Map<String, ?> headers, Integer id) {
        return webClient.delete()
                .uri(String.format("%s/%d", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Lista todos os webhooks existentes de forma síncrona.
     *
     * @param headers Cabeçalhos HTTP enviados na requisição.
     * @return {@link Page} contendo uma lista de {@link WebhookResponse}.
     */
    public Page<WebhookResponse> findAllWebhooks(Map<String, ?> headers) {
        return findAllWebhooksAsync(headers, null).block();
    }

    /**
     * Lista todos os webhooks existentes de forma síncrona com parâmetros de consulta.
     *
     * @param headers     Cabeçalhos HTTP enviados na requisição.
     * @param queryParams Parâmetros adicionais para personalizar a listagem de webhooks.
     * @return {@link Page} contendo uma lista de {@link WebhookResponse}.
     */
    public Page<WebhookResponse> findAllWebhooks(Map<String, ?> headers, Map<String, ?> queryParams) {
        return findAllWebhooksAsync(headers, queryParams).block();
    }

    /**
     * Lista todos os webhooks existentes de forma assíncrona.
     *
     * @param headers     Cabeçalhos HTTP enviados na requisição.
     * @param queryParams Parâmetros adicionais para personalizar a listagem de webhooks.
     * @return {@link Mono} contendo uma {@link Page} de {@link WebhookResponse}.
     */
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

    /**
     * Atualiza um webhook existente de forma síncrona.
     *
     * @param headers         Cabeçalhos HTTP enviados na requisição.
     * @param id              Identificador único do webhook a ser atualizado.
     * @param webhookRequest  Dados atualizados do webhook.
     * @return {@link WebhookResponse} contendo os detalhes do webhook atualizado.
     */
    public WebhookResponse updateWebhook(Map<String, ?> headers, Integer id, WebhookRequest webhookRequest) {
        return updateWebhookAsync(headers, id, webhookRequest).block();
    }

    /**
     * Atualiza um webhook existente de forma assíncrona.
     *
     * @param headers         Cabeçalhos HTTP enviados na requisição.
     * @param id              Identificador único do webhook a ser atualizado.
     * @param webhookRequest  Dados atualizados do webhook.
     * @return {@link Mono} contendo {@link WebhookResponse} com os detalhes do webhook atualizado.
     */
    public Mono<WebhookResponse> updateWebhookAsync(Map<String, ?> headers, Integer id, WebhookRequest webhookRequest) {
        return webClient.put()
                .uri(String.format("%s/%d", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(webhookRequest)
                .retrieve()
                .bodyToMono(WebhookResponse.class);
    }
}
