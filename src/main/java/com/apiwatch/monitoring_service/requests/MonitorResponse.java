package com.apiwatch.monitoring_service.requests;


import java.time.LocalDateTime;
import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorInterval;
import com.apiwatch.monitoring_service.enums.MonitorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorResponse {

    private UUID id;

    private UUID apiId;

    private String name;

    private MonitorType monitorType;

    private MonitorInterval interval;

    private Integer timeoutSeconds;

    private Integer expectedStatus;

    private String expectedResponse;

    private Boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}