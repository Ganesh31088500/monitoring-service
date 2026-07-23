package com.apiwatch.monitoring_service.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorInterval;
import com.apiwatch.monitoring_service.enums.MonitorType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMonitorRequest {

    @NotNull(message = "API Id is required")
    private UUID apiId;

    @NotBlank(message = "Monitor name is required")
    @Size(max = 150)
    private String name;

    @NotNull(message = "Monitor type is required")
    private MonitorType monitorType;

    @NotNull(message = "Monitor interval is required")
    private MonitorInterval interval;

    @NotNull(message = "Timeout is required")
    @Min(value = 1, message = "Timeout must be greater than 0")
    private Integer timeoutSeconds;

    @NotNull(message = "Expected HTTP Status is required")
    private Integer expectedStatus;

    @Size(max = 5000)
    private String expectedResponse;
    @NotNull
    private Boolean emailAlert;

    @NotNull
    private Boolean slackAlert;

    @NotNull
    private Boolean teamsAlert;

    @NotNull
    private Boolean webhookAlert;

    @NotNull
    private Boolean notifyOnRecovery;

    private Integer retryCount;

    private Integer failureThreshold;

    private String description;

}