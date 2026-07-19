package com.apiwatch.monitoring_service.requests;


import java.time.LocalDateTime;
import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorExecutionResponse {

    private UUID id;

    private UUID monitorId;
    private String monitorName;
    private MonitorStatus status;

    private Integer httpStatus;

    private Long responseTime;

    private String responseBody;

    private String errorMessage;

    private LocalDateTime executedAt;

}