package io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.model.Robot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RobotRestDelegate extends WhiskerDelegate implements RobotDelegate {

    private final RestTemplate restTemplate;
    private final WhiskerApiProperties whiskerApiProperties;

    public RobotRestDelegate(@Autowired RestTemplate restTemplate,
                             @Autowired WhiskerApiProperties whiskerApiProperties) {
        this.restTemplate = restTemplate;
        this.whiskerApiProperties = whiskerApiProperties;
    }

    public ResponseEntity<List<Robot>> getRobots(Map<String, String> params) {
        return restTemplate.exchange(whiskerApiProperties.getRobotsEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), new ParameterizedTypeReference<>() {
        }, params);
    }

    public ResponseEntity<Robot> getRobot(Map<String, String> params) {
        return restTemplate.exchange(whiskerApiProperties.getRobotEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), Robot.class, params);
    }


}
