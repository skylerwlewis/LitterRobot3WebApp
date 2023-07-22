package io.skylerlewis.litterrobot.litterrobotapp.api;

import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class WhiskerService {

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
