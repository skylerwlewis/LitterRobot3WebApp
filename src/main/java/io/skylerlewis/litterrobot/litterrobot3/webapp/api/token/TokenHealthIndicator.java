package io.skylerlewis.litterrobot.litterrobot3.webapp.api.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TokenHealthIndicator implements HealthIndicator {

    private final TokenService tokenService;

    public TokenHealthIndicator(@Autowired TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Health health() {
        String userId = tokenService.getUserId();
        String accessToken = tokenService.getAccessToken();
        Health.Builder healthBuilder = Health.down();
        if (userId != null && accessToken != null) {
            healthBuilder = Health.up();
        }
        return healthBuilder.build();
    }
}
