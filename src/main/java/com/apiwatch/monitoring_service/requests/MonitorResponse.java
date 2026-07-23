package com.apiwatch.monitoring_service.requests;


import java.time.LocalDateTime;
import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorInterval;
import com.apiwatch.monitoring_service.enums.MonitorStatus;
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

	    private UUID projectId;
	    private String projectName;

	    private UUID apiId;
	    private String apiName;

	    private String name;
	    private String description;

	    private MonitorType monitorType;
	    private MonitorInterval interval;

	    private Integer timeoutSeconds;

	    private Integer retryCount;
	    private Integer failureThreshold;

	    private Integer expectedStatus;
	    private String expectedResponse;

	    private Boolean emailAlert;
	    private Boolean slackAlert;
	    private Boolean teamsAlert;
	    private Boolean webhookAlert;
	    private Boolean notifyOnRecovery;

	    private Boolean enabled;

	    private MonitorStatus lastStatus;

	    private Double availability;
	    private Double averageResponseTime;

	    private LocalDateTime lastExecution;

	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}