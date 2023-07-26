package io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.model.Robot;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RobotService {

    private final RobotDelegate robotDelegate;
    private final TokenService tokenService;

    public RobotService(@Autowired RobotDelegate robotDelegate,
                        @Autowired TokenService tokenService) {
        this.robotDelegate = robotDelegate;
        this.tokenService = tokenService;
    }

    public List<Robot> getRobots() {
        List<Robot> robots = null;

        Map<String, String> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());

        ResponseEntity<List<Robot>> response = robotDelegate.getRobots(params);
        if (response != null && response.getBody() != null) {
            log.info("Successfully retrieved robots: {}", response.getBody());
            robots = response.getBody();
        }
        return robots;
    }

    public Robot getRobot(String robotId) {
        Robot robot = null;

        Map<String, String> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);

        ResponseEntity<Robot> response = robotDelegate.getRobot(params);
        if (response != null && response.getBody() != null) {
            log.info("Successfully retrieved robots: {}", response.getBody());
            robot = response.getBody();
        }
        return robot;
    }

}
