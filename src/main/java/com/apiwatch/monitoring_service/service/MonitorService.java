package com.apiwatch.monitoring_service.service;

import java.util.List;
import java.util.UUID;

import com.apiwatch.monitoring_service.entity.MonitorStatisticsResponse;
import com.apiwatch.monitoring_service.requests.CreateMonitorRequest;
import com.apiwatch.monitoring_service.requests.MonitorExecutionResponse;
import com.apiwatch.monitoring_service.requests.MonitorResponse;
import com.apiwatch.monitoring_service.requests.UpdateMonitorRequest;



public interface MonitorService {
	  MonitorStatisticsResponse getStatistics(UUID apiId);
    /**
     * Create Monitor
     */
    MonitorResponse createMonitor(
            CreateMonitorRequest request);

    /**
     * Get Monitor By Id
     */
    MonitorResponse getMonitor(
            UUID monitorId);

    /**
     * Get All Monitors
     */
    List<MonitorResponse> getAllMonitors();
    List<MonitorExecutionResponse> getRecentExecutions();
    /**
     * Get Monitors By API
     */
    List<MonitorResponse> getApiMonitors(
            UUID apiId);

    /**
     * Update Monitor
     */
    MonitorResponse updateMonitor(
            UUID monitorId,
            UpdateMonitorRequest request);

    /**
     * Delete Monitor
     */
    void deleteMonitor(
            UUID monitorId);

    /**
     * Execute Monitor
     */
    MonitorExecutionResponse executeMonitor(
            UUID monitorId);

    /**
     * Get Execution History
     */
    List<MonitorExecutionResponse> getExecutionHistory(
            UUID monitorId);

    /**
     * Enable Monitor
     */
    MonitorResponse enableMonitor(
            UUID monitorId);

    /**
     * Disable Monitor
     */
    MonitorResponse disableMonitor(
            UUID monitorId);

}