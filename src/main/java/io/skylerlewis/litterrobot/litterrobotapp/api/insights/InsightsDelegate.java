package io.skylerlewis.litterrobot.litterrobotapp.api.insights;

import io.skylerlewis.litterrobot.litterrobotapp.api.insights.model.Insights;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface InsightsDelegate {

    ResponseEntity<Insights> getRobotInsights(Map<String, String> params);

}
