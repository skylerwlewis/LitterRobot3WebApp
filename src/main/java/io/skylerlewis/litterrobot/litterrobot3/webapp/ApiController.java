package io.skylerlewis.litterrobot.litterrobot3.webapp;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.InsightsService;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.model.Insights;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.UserService;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model.User;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.ActivityService;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.model.ActivityHistory;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.RobotService;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.robot.model.Robot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    private final UserService userService;
    private final RobotService robotService;
    private final ActivityService activityService;
    private final InsightsService insightsService;

    public ApiController(
            @Autowired UserService userService,
            @Autowired RobotService robotService,
            @Autowired ActivityService activityService,
            @Autowired InsightsService insightsService) {
        this.userService = userService;
        this.robotService = robotService;
        this.activityService = activityService;
        this.insightsService = insightsService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser() {
        return userService.getUser();
    }

    @RequestMapping(value = "/robot/{robotId}", method = RequestMethod.GET)
    public Robot getRobot(@PathVariable String robotId) {
        Robot robot = null;
        robot = robotService.getRobot(robotId);
        return robot;
    }

    @RequestMapping(value = "/robot/{robotId}/activity/{limit}", method = RequestMethod.GET)
    public ActivityHistory getActivity(@PathVariable String robotId, @PathVariable Integer limit) {
        ActivityHistory activityHistory = null;
        activityHistory = activityService.getActivityHistory(robotId, limit);
        return activityHistory;
    }

    @RequestMapping(value = "/robot/{robotId}/insights/{days}", method = RequestMethod.GET)
    public Insights getInsights(@PathVariable String robotId, @PathVariable Integer days) {
        Insights insights = null;
        insights = insightsService.getRobotInsights(robotId, days);
        return insights;
    }

}
