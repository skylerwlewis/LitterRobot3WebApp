package io.skylerlewis.litterrobot.litterrobot3.webapp.api.token;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerAuthProperties;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.token.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenRestDelegate implements TokenDelegate {

    private final RestTemplate restTemplate;
    private final WhiskerAuthProperties whiskerAuthProperties;

    public TokenRestDelegate(@Autowired RestTemplate restTemplate,
                             @Autowired WhiskerAuthProperties whiskerAuthProperties) {
        this.restTemplate = restTemplate;
        this.whiskerAuthProperties = whiskerAuthProperties;
    }

    public ResponseEntity<TokenResponse> getTokenResponse(HttpEntity<MultiValueMap<String, String>> httpEntity) {
        return restTemplate.exchange(whiskerAuthProperties.getAuthEndpoint(), HttpMethod.POST, httpEntity, TokenResponse.class);
    }

}
