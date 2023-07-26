package io.skylerlewis.litterrobot.litterrobot3.webapp.api.token;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.token.model.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface TokenDelegate {

    ResponseEntity<TokenResponse> getTokenResponse(HttpEntity<MultiValueMap<String, String>> httpEntity);

}
