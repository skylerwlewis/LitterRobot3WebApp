package io.skylerlewis.litterrobot.litterrobot3.webapp.api;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class WhiskerDelegate {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WhiskerApiProperties whiskerApiProperties;

    protected HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + tokenService.getAccessToken());
        requestHeaders.add("x-api-key", whiskerApiProperties.getDefaultEndpointKey());
        return requestHeaders;
    }

}
