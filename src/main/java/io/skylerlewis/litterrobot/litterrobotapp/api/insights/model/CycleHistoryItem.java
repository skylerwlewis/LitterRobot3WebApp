package io.skylerlewis.litterrobot.litterrobotapp.api.insights.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CycleHistoryItem {

    LocalDate date;
    Long cyclesCompleted;

}
