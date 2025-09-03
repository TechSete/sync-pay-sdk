package tech.techsete.sync_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.sync_pay_sdk.dtos.request.AuthenticationRequest;
import tech.techsete.sync_pay_sdk.dtos.response.AuthenticationResponse;

@Service(value = "syncPayAuthenticationService")
public class AuthenticationService {

    private final String AUTHENTICATE_URI = "/api/partner/v1/auth-token";
    private final WebClient webClient;

    public AuthenticationService(@Qualifier("syncPayWebClient")
                                 WebClient webClient
    ) {
        this.webClient = webClient;
    }

    public AuthenticationResponse authenticate(String clientId, String clientSecret) {
        return authenticate(new AuthenticationRequest(clientId, clientSecret));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        return webClient.post()
                .uri(AUTHENTICATE_URI)
                .bodyValue(authenticationRequest)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .block();
    }

    public Mono<AuthenticationResponse> authenticateAsync(String clientId, String clientSecret) {
        return authenticateAsync(new AuthenticationRequest(clientId, clientSecret));
    }

    public Mono<AuthenticationResponse> authenticateAsync(AuthenticationRequest authenticationRequest) {
        return webClient.post()
                .uri(AUTHENTICATE_URI)
                .bodyValue(authenticationRequest)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }
}