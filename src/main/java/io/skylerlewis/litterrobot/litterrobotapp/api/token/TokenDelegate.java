package io.skylerlewis.litterrobot.litterrobotapp.api.token;

import io.skylerlewis.litterrobot.litterrobotapp.api.token.model.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface TokenDelegate {

    public ResponseEntity<TokenResponse> getTokenResponse(HttpEntity<MultiValueMap<String, String>> httpEntity);

}
