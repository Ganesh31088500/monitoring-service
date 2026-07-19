package com.apiwatch.monitoring_service.requests;


import com.apiwatch.monitoring_service.enums.MonitorInterval;
import com.apiwatch.monitoring_service.enums.MonitorType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMonitorRequest {

    @Size(max = 150)
    private String name;

    private MonitorType monitorType;

    private MonitorInterval interval;

    @Min(value = 1)
    private Integer timeoutSeconds;

    private Integer expectedStatus;

    @Size(max = 5000)
    private String expectedResponse;

    private Boolean enabled;

}