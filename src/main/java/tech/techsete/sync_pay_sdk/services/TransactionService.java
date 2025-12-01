package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.dtos.response.TransactionResponse;

import java.util.Map;

/**
 * Serviço responsável pela gestão de transações na API SyncPay.
 * <p>
 * Esta classe realiza chamadas à API SyncPay para obter informações sobre transações,
 * como detalhes de status e dados associados às transações realizadas.
 * Oferece métodos para busca síncrona e assíncrona.
 * </p>
 *
 * <p><b>Endpoints utilizados:</b></p>
 * <ul>
 *     <li><b>URI Base:</b> {@code /api/partner/v1/transaction}</li>
 * </ul>
 *
 * @see WebClient
 * @see tech.techsete.sync_pay_sdk.dtos.response.TransactionResponse
 */
@Service("syncPayTransactionService")
public class TransactionService {

    private final String baseURL = "/api/partner/v1/transaction";
    private final WebClient webClient;

    /**
     * Construtor responsável por configurar o cliente HTTP {@link WebClient}.
     *
     * @param webClient O cliente HTTP configurado para comunicação com a API SyncPay, providenciado pelo contexto Spring.
     */
    public TransactionService(@Qualifier("syncPayWebClient")
                              WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Busca os detalhes de uma transação utilizando um identificador fornecido, de forma síncrona.
     *
     * @param headers Cabeçalhos HTTP a serem enviados na requisição para autenticação e controle.
     * @param id      O identificador único da transação a ser retornada.
     * @return {@link TransactionResponse} contendo os dados detalhados da transação.
     */
    public TransactionResponse findTransaction(Map<String, ?> headers, String id) {
        return findTransactionAsync(headers, id).block();
    }

    /**
     * Busca os detalhes de uma transação utilizando um identificador fornecido, de forma assíncrona.
     * <p>
     * Este método retorna um {@link Mono} que será resolvido com os dados da transação após a resposta da API.
     * </p>
     *
     * @param headers Cabeçalhos HTTP a serem enviados na requisição para autenticação e controle.
     * @param id      O identificador único da transação a ser retornada.
     * @return {@link Mono} contendo {@link TransactionResponse} com os dados detalhados da transação.
     */
    public Mono<TransactionResponse> findTransactionAsync(Map<String, ?> headers, String id) {
        return webClient.get()
                .uri(String.format("%s/%s", baseURL, id))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(TransactionResponse.class);
    }
}