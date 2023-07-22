package io.skylerlewis.litterrobot.litterrobotapp;

import io.skylerlewis.litterrobot.litterrobotapp.api.activity.ActivityService;
import io.skylerlewis.litterrobot.litterrobotapp.api.activity.model.ActivityHistory;
import io.skylerlewis.litterrobot.litterrobotapp.api.insights.InsightsService;
import io.skylerlewis.litterrobot.litterrobotapp.api.insights.model.Insights;
import io.skylerlewis.litterrobot.litterrobotapp.api.robot.RobotService;
import io.skylerlewis.litterrobot.litterrobotapp.api.robot.model.Robot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class Controller {

    private RobotService robotService;
    private ActivityService activityService;
    private InsightsService insightsService;

    private String litterRobotId;

    public Controller(@Autowired RobotService robotService,
                      @Autowired ActivityService activityService,
                      @Autowired InsightsService insightsService) {
        this.robotService = robotService;
        this.activityService = activityService;
        this.insightsService = insightsService;

        Optional<String> litterRobotId = robotService.getRobots().stream().map(Robot::getLitterRobotId).findFirst();
        if(litterRobotId.isPresent()) {
            this.litterRobotId = litterRobotId.get();
        }
    }

    @RequestMapping("/robot")
    public Robot getRobot() {
        Robot robot = null;
        if(litterRobotId != null) {
            robot = robotService.getRobot(litterRobotId);
        }
        return robot;
    }

    @RequestMapping("/activity/{limit}")
    public ActivityHistory getActivity(@PathVariable Integer limit) {
        ActivityHistory activityHistory = null;
        if(litterRobotId != null) {
            activityHistory = activityService.getActivityHistory(litterRobotId, limit);
        }
        return activityHistory;
    }

    @RequestMapping("/insights/{days}")
    public Insights getInsights(@PathVariable Integer days) {
        Insights insights = null;
        if(litterRobotId != null) {
            insights = insightsService.getRobotInsights(litterRobotId, days);
        }
        return insights;
    }

}
