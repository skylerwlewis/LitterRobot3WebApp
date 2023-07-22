package io.skylerlewis.litterrobot.litterrobotapp.api.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerAuthProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.model.TokenPayload;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.model.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class TokenService {

    private static String GRANT_TYPE_PASSWORD = "password";
    private static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    private TokenResponse cachedTokenResponse;
    private DecodedJWT cachedJwt;
    private TokenPayload cachedTokenPayload;

    @Autowired
    private WhiskerAuthProperties whiskerAuthProperties;

    public TokenService(@Autowired RestTemplate restTemplate, @Autowired ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public String getAccessToken() {
        refreshTokenIfNeeded();
        return cachedTokenResponse.getAccessToken();
    }

    public String getUserId() {
        refreshTokenIfNeeded();
        return cachedTokenPayload.getUserId();
    }

    private void refreshTokenIfNeeded() {
        if (cachedTokenResponse == null || !isTokenValid(cachedJwt, ZonedDateTime.now().plus(whiskerAuthProperties.getEarlyRefreshMinutes(), ChronoUnit.MINUTES).toInstant())) {

            TokenResponse tokenResponse = null;
            if(cachedTokenResponse != null && cachedTokenResponse.getRefreshToken() != null) {
                log.info("Refreshing existing token");
                MultiValueMap<String, String> request = getRefreshRequest(cachedTokenResponse.getRefreshToken());
                tokenResponse = getTokenResponse(new HttpEntity<>(request, getRequestHeaders()));
            }
            if(tokenResponse == null) {
                log.info("Obtaining new token for {}", whiskerAuthProperties.getUsername());
                MultiValueMap<String, String> request = getLoginRequest();
                tokenResponse = getTokenResponse(new HttpEntity<>(request, getRequestHeaders()));
            }

            if (tokenResponse != null) {
                cachedTokenResponse = tokenResponse;
                if (cachedTokenResponse.getAccessToken() != null) {
                    try {
                        cachedJwt = JWT.decode(cachedTokenResponse.getAccessToken());
                    } catch (JWTDecodeException e) {
                        log.error("There was a problem decoding the JWT.", e);
                    }

                    if (cachedJwt != null && cachedJwt.getPayload() != null) {
                        try {
                            String jsonString = new String(Base64.getDecoder().decode(cachedJwt.getPayload()));
                            cachedTokenPayload = mapper.readValue(jsonString, TokenPayload.class);
                            log.info("Payload: {}", cachedTokenPayload);
                        } catch (JsonMappingException e) {
                            log.error("There was a problem mapping the token payload into an object.", e);
                        } catch (JsonProcessingException e) {
                            log.error("There was a problem processing the token payload into an object.", e);
                        }
                    }
                }
            }
        }
    }

    private TokenResponse getTokenResponse(HttpEntity<MultiValueMap> httpEntity) {
        TokenResponse tokenResponse = null;
        ResponseEntity<TokenResponse> response = restTemplate.exchange(whiskerAuthProperties.getAuthEndpoint(), HttpMethod.POST, httpEntity, TokenResponse.class);
        if (response != null && !response.getStatusCode().isError() && response.getBody() != null && response.getBody().getAccessToken() != null) {
            log.info("Successfully retrieved token response: {}", response.getBody());
            tokenResponse = response.getBody();
        }
        return tokenResponse;
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return requestHeaders;
    }

    private MultiValueMap<String, String> getRefreshRequest(String refreshToken) {
        MultiValueMap<String, String> refreshRequest = new LinkedMultiValueMap<>();
        refreshRequest.put("client_id", Arrays.asList(whiskerAuthProperties.getClientId()));
        refreshRequest.put("client_secret", Arrays.asList(whiskerAuthProperties.getClientSecret()));
        refreshRequest.put("grant_type", Arrays.asList(GRANT_TYPE_REFRESH_TOKEN));
        refreshRequest.put("refresh_token", Arrays.asList(refreshToken));
        return refreshRequest;
    }

    private MultiValueMap<String, String> getLoginRequest() {
        MultiValueMap<String, String> loginRequest = new LinkedMultiValueMap<>();
        loginRequest.put("client_id", Arrays.asList(whiskerAuthProperties.getClientId()));
        loginRequest.put("client_secret", Arrays.asList(whiskerAuthProperties.getClientSecret()));
        loginRequest.put("grant_type", Arrays.asList(GRANT_TYPE_PASSWORD));
        loginRequest.put("username", Arrays.asList(whiskerAuthProperties.getUsername()));
        loginRequest.put("password", Arrays.asList(whiskerAuthProperties.getPassword()));
        return loginRequest;
    }

    private boolean isTokenValid(DecodedJWT jwt, Instant atTime) {
        if (atTime != null && jwt != null && jwt.getExpiresAtAsInstant() != null) {
            return atTime.isBefore(jwt.getExpiresAtAsInstant());
        } else {
            return false;
        }
    }

}
