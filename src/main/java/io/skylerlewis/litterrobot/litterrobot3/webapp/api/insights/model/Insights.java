package io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class Insights {

    private Long totalCycles;
    private BigDecimal averageCycles;
    private List<CycleHistoryItem> cycleHistory;

}
