package io.skylerlewis.litterrobot.litterrobotapp;

import io.skylerlewis.litterrobot.litterrobotapp.api.activity.model.ActivityHistory;
import io.skylerlewis.litterrobot.litterrobotapp.api.insights.model.Insights;
import io.skylerlewis.litterrobot.litterrobotapp.api.robot.model.Robot;
import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.User;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ApiControllerTest {

    private ApiController apiController;

    @BeforeEach
    public void setup(@Autowired ApiController apiController) {
        this.apiController = apiController;
    }

    @Test
    public void testGetUser() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getUser() == null) {
            fail("There was a problem getting the user, user details was null");
        }

        assertThat(user.getUser().getUserId(), equalTo("100"));
    }

    @Test
    public void testGetRobot() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        Robot robot = apiController.getRobot(user.getLitterRobots().get(0).getLitterRobotId());

        if (robot == null) {
            fail("There was a problem getting the robot, it was null");
        }

        assertThat(robot.getLitterRobotSerial(), equalTo("cereal-number-123"));
        assertThat(robot.getLitterRobotNickname(), equalTo("Our Cat's Litterbox"));
    }

    @Test
    public void testGetRobotMissing() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        try {
            apiController.getRobot(user.getLitterRobots().get(0).getLitterRobotId() + "-extra-junk");
            fail("Robot was found and should not have been");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void testGetActivities() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        ActivityHistory activityHistory = apiController.getActivity(user.getLitterRobots().get(0).getLitterRobotId(), 12);

        if (activityHistory == null || activityHistory.getActivities() == null) {
            fail("There was a problem getting the activity history, it was null");
        }

        assertThat(activityHistory.getActivities(), is(IsCollectionWithSize.hasSize(12)));
    }

    @Test
    public void testGetActivitiesMissing() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        try {
            apiController.getActivity(user.getLitterRobots().get(0).getLitterRobotId() + "-other-stuff", 12);
            fail("Activities were found and should not have been");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void testGetInsights() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        Insights insights = apiController.getInsights(user.getLitterRobots().get(0).getLitterRobotId(), 12);

        if (insights == null || insights.getCycleHistory() == null) {
            fail("There was a problem getting the insights cycle history, it was null");
        }

        assertThat(insights.getCycleHistory(), is(IsCollectionWithSize.hasSize(30)));
    }

    @Test
    public void testGetInsightsMissing() {
        User user = apiController.getUser();

        if (user == null) {
            fail("There was a problem getting the user, it was null");
        }

        if (user.getLitterRobots() == null || user.getLitterRobots().size() != 1 || user.getLitterRobots().get(0).getLitterRobotId() == null) {
            fail("There was a problem getting the user, litter robot ID was missing");
        }

        try {
            apiController.getInsights(user.getLitterRobots().get(0).getLitterRobotId() + "-other-stuff", 12);
            fail("Insights were found and should not have been");
        } catch (ResponseStatusException e) {
            assertThat(e.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }

}
