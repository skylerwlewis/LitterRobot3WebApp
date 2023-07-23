package io.skylerlewis.litterrobot.litterrobotapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenDelegate;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.model.TokenPayload;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Component
public class TestTokenDelegate implements TokenDelegate {

    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private static final Set<String> ALLOWED_GRANT_TYPES = new HashSet<>() {{
        this.add(GRANT_TYPE_PASSWORD);
        this.add(GRANT_TYPE_REFRESH_TOKEN);
    }};

    private static final Map<String, UserInfo> TEST_USERS = new HashMap<>() {{
        this.put("test@example.com", new UserInfo("100", "testing-password"));
    }};

    private static final Map<String, String> TEST_CLIENTS = new HashMap<>() {{
        this.put("test-client-id", "test-client-secret");
    }};

    private final Map<String, UserInfo> refreshTokens;
    private final ObjectMapper mapper;
    private final Algorithm algorithm;

    public TestTokenDelegate(@Autowired ObjectMapper mapper) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.mapper = mapper;
        this.refreshTokens = new HashMap<>();
        this.algorithm = Algorithm.RSA256(readPublicKey("public.pem"), readPrivateKey("private.pem"));
    }

    @Override
    public ResponseEntity<TokenResponse> getTokenResponse(HttpEntity<MultiValueMap<String, String>> httpEntity) {
        MultiValueMap<String, String> request = httpEntity.getBody();

        TokenResponse response = null;

        String clientId = null;
        if (request.get("client_id") != null && request.get("client_id").size() == 1) {
            clientId = request.get("client_id").get(0);
            if (!TEST_CLIENTS.containsKey(clientId)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "client_id is not in clients list");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one client_id is required");
        }

        String clientSecret = null;
        if (request.get("client_secret") != null && request.get("client_secret").size() == 1) {
            clientSecret = request.get("client_secret").get(0);
            if (!TEST_CLIENTS.get(clientId).equals(clientSecret)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "client_secret is incorrect");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one client_secret is required");
        }

        String grantType = null;
        if (request.get("grant_type") != null && request.get("grant_type").size() == 1) {
            grantType = request.get("grant_type").get(0);
            if (!ALLOWED_GRANT_TYPES.contains(grantType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grant_type is not either " + ALLOWED_GRANT_TYPES.stream().collect(Collectors.joining(" or ")));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one grant_type is required");
        }

        if (GRANT_TYPE_PASSWORD.equals(grantType)) {

            String username = null;
            if (request.get("username") != null && request.get("username").size() == 1) {
                username = request.get("username").get(0);
                if (!TEST_USERS.containsKey(username)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username is not in users list");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one username is required");
            }

            UserInfo userInfo = TEST_USERS.get(username);

            String password = null;
            if (request.get("password") != null && request.get("password").size() == 1) {
                password = request.get("password").get(0);
                if (!userInfo.getPassword().equals(password)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "password is incorrect");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one password is required");
            }

            TokenPayload tokenPayload = new TokenPayload();
            tokenPayload.setUserId(userInfo.getUserId());
            tokenPayload.setClientId(clientId);
            tokenPayload.setIssuedAt(Instant.now());
            tokenPayload.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));

            String token = getAccessToken(tokenPayload);

            String refreshToken = UUID.randomUUID().toString();
            refreshTokens.put(refreshToken, userInfo);

            response = new TokenResponse();

            response.setAccessToken(token);
            response.setRefreshToken(refreshToken);
            response.setExpiresIn(300L);
            response.setTokenType("Bearer");

        } else if (GRANT_TYPE_REFRESH_TOKEN.equals(grantType)) {
            String refreshToken = null;
            if (request.get("refresh_token") != null && request.get("refresh_token").size() == 1) {
                refreshToken = request.get("refresh_token").get(0);
                if (!refreshTokens.containsKey(refreshToken)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "refresh_token is incorrect");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exactly one refresh_token is required");
            }

            UserInfo userInfo = refreshTokens.get(refreshToken);

            TokenPayload tokenPayload = new TokenPayload();
            tokenPayload.setUserId(userInfo.getUserId());
            tokenPayload.setClientId(clientId);
            tokenPayload.setIssuedAt(Instant.now());
            tokenPayload.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));

            String token = getAccessToken(tokenPayload);

            response = new TokenResponse();

            response.setAccessToken(token);
            response.setRefreshToken(refreshToken);
            response.setExpiresIn(300L);
        }

        return ResponseEntity.ok(response);
    }

    private String getAccessToken(TokenPayload tokenPayload) {
        String jsonPayload = null;
        try {
            jsonPayload = mapper.writeValueAsString(tokenPayload);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was an internal server error");
        }

        String token = JWT.create().withPayload(jsonPayload).sign(algorithm);

        return token;
    }

    private RSAPublicKey readPublicKey(String resourcePath) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String key = new String(this.getClass().getClassLoader()
                .getResourceAsStream(resourcePath).readAllBytes(), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey readPrivateKey(String resourcePath) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String key = new String(this.getClass().getClassLoader()
                .getResourceAsStream(resourcePath).readAllBytes(), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

}
