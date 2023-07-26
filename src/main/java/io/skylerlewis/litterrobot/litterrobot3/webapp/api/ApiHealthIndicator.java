package io.skylerlewis.litterrobot.litterrobot3.webapp.api;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.UserService;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ApiHealthIndicator implements HealthIndicator {

    private final UserService userService;

    public ApiHealthIndicator(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public Health health() {
        User user = userService.getUser();
        Health.Builder healthBuilder = Health.down();
        if (user != null && user.getUser() != null && user.getLitterRobots() != null && user.getLitterRobots().size() > 0) {
            healthBuilder = Health.up();
        }
        return healthBuilder.build();
    }

}
