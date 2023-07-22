package io.skylerlewis.litterrobot.litterrobotapp.api.robot;

import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerService;
import io.skylerlewis.litterrobot.litterrobotapp.api.robot.model.Robot;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RobotService extends WhiskerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WhiskerApiProperties whiskerApiProperties;

    @Autowired
    private TokenService tokenService;

    public List<Robot> getRobots() {
        List<Robot> robots = null;

        Map<String, String> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());

        ResponseEntity<List<Robot>> response = restTemplate.exchange(whiskerApiProperties.getRobotsEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), new ParameterizedTypeReference<>() {}, params);
        if(response != null && response.getBody() != null) {
            log.info("Successfully retrieved robots: {}", response.getBody());
            robots = response.getBody();
        }
        return robots;
    }

    public Robot getRobot(String robotId) {
        Robot robot = null;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);

        ResponseEntity<Robot> response = restTemplate.exchange(whiskerApiProperties.getRobotEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), Robot.class, params);
        if(response != null && response.getBody() != null) {
            log.info("Successfully retrieved robots: {}", response.getBody());
            robot = response.getBody();
        }
        return robot;
    }

}
