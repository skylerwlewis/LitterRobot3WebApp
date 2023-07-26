package io.skylerlewis.litterrobot.litterrobot3.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.ActivityDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.model.ActivityHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Primary
@Component
public class TestActivityDelegate implements ActivityDelegate {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public TestActivityDelegate(@Autowired ObjectMapper mapper,
                                @Autowired ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<ActivityHistory> getActivityHistory(Map<String, String> params) {
        ResponseEntity<ActivityHistory> response = null;
        String robotsJsonPath = "unitTestJsonFiles/" + params.get("userId") + "/" + params.get("robotId") + "/activity.json";

        Resource resource = resourceLoader.getResource("classpath:" + robotsJsonPath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no insights content");
        }

        ActivityHistory activityHistory = null;
        try {
            activityHistory = mapper.readValue(resource.getInputStream(), ActivityHistory.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was a problem reading the insights json file");
        }

        response = ResponseEntity.ok(activityHistory);

        return response;
    }
}
