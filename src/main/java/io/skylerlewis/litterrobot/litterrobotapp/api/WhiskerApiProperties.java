package io.skylerlewis.litterrobot.litterrobotapp.api;

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
    @Value("${whisker.api.default}${whisker.api.endpointPaths.robot}")
    private String robotEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.activity}")
    private String activityEndpoint;
    @Value("${whisker.api.default}${whisker.api.endpointPaths.insights}")
    private String insightsEndpoint;

}
