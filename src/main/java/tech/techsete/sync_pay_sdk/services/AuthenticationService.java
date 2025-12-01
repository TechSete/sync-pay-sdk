package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.dtos.request.AuthenticationRequest;
import tech.techsete.sync_pay_sdk.dtos.response.AuthenticationResponse;

/**
 * Serviço responsável pela autenticação na API SyncPay.
 * <p>
 * Esta classe realiza chamadas à API SyncPay para autenticar um cliente
 * e obter um token de acesso. Oferece métodos para autenticação síncrona
 * e assíncrona.
 * </p>
 *
 * <p><b>Endpoints utilizados:</b></p>
 * <ul>
 *     <li><b>URI de autenticação:</b> {@code /api/partner/v1/auth-token}</li>
 * </ul>
 *
 * @see WebClient
 * @see tech.techsete.sync_pay_sdk.dtos.request.AuthenticationRequest
 * @see tech.techsete.sync_pay_sdk.dtos.response.AuthenticationResponse
 */

@Service(value = "syncPayAuthenticationService")
public class AuthenticationService {

    private final String AUTHENTICATE_URI = "/api/partner/v1/auth-token";
    private final WebClient webClient;

    /**
     * Construtor para inicializar o {@link WebClient} utilizado nas chamadas HTTP.
     *
     * @param webClient O cliente HTTP configurado para comunicação com a API SyncPay.
     *                  Este cliente deve ser providenciado pelo contexto Spring e estar
     *                  configurado com os parâmetros necessários (exemplo: URL base).
     */

    public AuthenticationService(@Qualifier("syncPayWebClient")
                                 WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Realiza autenticação síncrona com a API utilizando o clientId e clientSecret.
     *
     * @param clientId     O identificador do cliente utilizado na autenticação.
     * @param clientSecret O segredo do cliente utilizado na autenticação.
     * @return {@link AuthenticationResponse} contendo o token de acesso e informações adicionais.
     */

    public AuthenticationResponse authenticate(String clientId, String clientSecret) {
        return authenticate(new AuthenticationRequest(clientId, clientSecret));
    }

    /**
     * Realiza autenticação síncrona com a API utilizando uma requisição completa.
     *
     * @param authenticationRequest O objeto contendo os dados necessários para autenticação.
     * @return {@link AuthenticationResponse} contendo o token de acesso e informações adicionais.
     */

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        return webClient.post()
                .uri(AUTHENTICATE_URI)
                .bodyValue(authenticationRequest)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .block();
    }

    /**
     * Realiza autenticação assíncrona com a API utilizando o clientId e clientSecret.
     *
     * @param clientId     O identificador do cliente utilizado na autenticação.
     * @param clientSecret O segredo do cliente utilizado na autenticação.
     * @return {@link Mono} contendo a resposta {@link AuthenticationResponse} assinada reativamente.
     */
    public Mono<AuthenticationResponse> authenticateAsync(String clientId, String clientSecret) {
        return authenticateAsync(new AuthenticationRequest(clientId, clientSecret));
    }

    /**
     * Realiza autenticação assíncrona com a API utilizando uma requisição completa.
     *
     * @param authenticationRequest O objeto contendo os dados necessários para autenticação.
     * @return {@link Mono} contendo a resposta {@link AuthenticationResponse} assinada reativamente.
     */
    public Mono<AuthenticationResponse> authenticateAsync(AuthenticationRequest authenticationRequest) {
        return webClient.post()
                .uri(AUTHENTICATE_URI)
                .bodyValue(authenticationRequest)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }
}