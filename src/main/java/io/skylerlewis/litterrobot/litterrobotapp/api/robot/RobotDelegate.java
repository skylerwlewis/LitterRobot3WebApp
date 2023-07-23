package io.skylerlewis.litterrobot.litterrobotapp.api.robot;

import io.skylerlewis.litterrobot.litterrobotapp.api.robot.model.Robot;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RobotDelegate {

    ResponseEntity<List<Robot>> getRobots(Map<String, String> params);

    ResponseEntity<Robot> getRobot(Map<String, String> params);

}
