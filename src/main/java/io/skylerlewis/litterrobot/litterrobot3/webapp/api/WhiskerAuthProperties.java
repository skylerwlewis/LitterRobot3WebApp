package io.skylerlewis.litterrobot.litterrobot3.webapp.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiskerAuthProperties {

    @Value("${whisker.auth.endpoint}")
    private String authEndpoint;
    @Value("${whisker.auth.clientId}")
    private String clientId;
    @Value("${whisker.auth.clientSecret}")
    private String clientSecret;

    @Value("${whisker.auth.credentials.username}")
    private String username;
    @Value("${whisker.auth.credentials.password}")
    private String password;

    @Value("${whisker.auth.early-refresh-minutes:0}")
    private Integer earlyRefreshMinutes;

}
