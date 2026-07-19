package com.apiwatch.monitoring_service.entity;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorStatisticsResponse {

    private Long averageResponseTime;

    private LocalDateTime lastExecution;

    private Long successCount;

    private Long failureCount;

}