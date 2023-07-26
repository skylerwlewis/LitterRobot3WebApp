package io.skylerlewis.litterrobot.litterrobot3.webapp.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiskerApiProperties {

    @Value("${whisker.api.key}")
    private String defaultEndpointKey;

    @Value("${whisker.api.default}${whisker.api.endpointPaths.users}")
    private String usersEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.robots}")
    private String robotsEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.robot.details}")
    private String robotEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.robot.activity}")
    private String activityEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.robot.insights}")
    private String insightsEndpoint;

}
