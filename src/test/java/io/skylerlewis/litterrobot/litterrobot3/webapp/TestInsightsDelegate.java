package io.skylerlewis.litterrobot.litterrobot3.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.model.Insights;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.InsightsDelegate;
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
public class TestInsightsDelegate implements InsightsDelegate {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public TestInsightsDelegate(@Autowired ObjectMapper mapper,
                                @Autowired ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<Insights> getRobotInsights(Map<String, String> params) {
        ResponseEntity<Insights> response = null;
        String robotsJsonPath = "unitTestJsonFiles/" + params.get("userId") + "/" + params.get("robotId") + "/insights.json";

        Resource resource = resourceLoader.getResource("classpath:" + robotsJsonPath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no insights content");
        }

        Insights insights = null;
        try {
            insights = mapper.readValue(resource.getInputStream(), Insights.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was a problem reading the insights json file");
        }

        response = ResponseEntity.ok(insights);

        return response;
    }
}
