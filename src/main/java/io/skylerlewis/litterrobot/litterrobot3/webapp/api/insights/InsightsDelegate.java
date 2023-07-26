package io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.model.Insights;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface InsightsDelegate {

    ResponseEntity<Insights> getRobotInsights(Map<String, String> params);

}
