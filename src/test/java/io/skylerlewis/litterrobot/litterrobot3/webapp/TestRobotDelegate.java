package io.skylerlewis.litterrobot.litterrobot3.webapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.RobotDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.model.Robot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class TestRobotDelegate implements RobotDelegate {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public TestRobotDelegate(@Autowired ObjectMapper mapper,
                             @Autowired ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<List<Robot>> getRobots(Map<String, String> params) {
        ResponseEntity<List<Robot>> response = null;
        String robotsJsonPath = "unitTestJsonFiles/" + params.get("userId") + "/robots.json";

        Resource resource = resourceLoader.getResource("classpath:" + robotsJsonPath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no robots content");
        }

        List<Robot> robots = null;
        try {
            robots = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was a problem reading the robots json file");
        }

        response = ResponseEntity.ok(robots);

        return response;
    }

    @Override
    public ResponseEntity<Robot> getRobot(Map<String, String> params) {
        ResponseEntity<Robot> response = null;
        String robotsJsonPath = "unitTestJsonFiles/" + params.get("userId") + "/" + params.get("robotId") + "/robot.json";

        Resource resource = resourceLoader.getResource("classpath:" + robotsJsonPath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no robot content");
        }

        Robot robot = null;
        try {
            robot = mapper.readValue(resource.getInputStream(), Robot.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was a problem reading the robot json file");
        }

        response = ResponseEntity.ok(robot);

        return response;
    }
}
