package com.apiwatch.monitoring_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.apiwatch.monitoring_service.entity.MonitorStatisticsResponse;
import com.apiwatch.monitoring_service.requests.CreateMonitorRequest;
import com.apiwatch.monitoring_service.requests.MonitorExecutionResponse;
import com.apiwatch.monitoring_service.requests.MonitorResponse;
import com.apiwatch.monitoring_service.requests.UpdateMonitorRequest;
import com.apiwatch.monitoring_service.service.MonitorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/monitors")
@RequiredArgsConstructor
@Validated
public class MonitorController {

    private final MonitorService monitorService;
    @GetMapping("/recent-executions")
    public ResponseEntity<List<MonitorExecutionResponse>> getRecentExecutions() {

        return ResponseEntity.ok(
                monitorService.getRecentExecutions());

    }
    /**
     * Create Monitor
     */
    @PostMapping
    public ResponseEntity<MonitorResponse> createMonitor(
            @Valid @RequestBody CreateMonitorRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(monitorService.createMonitor(request));

    }

    /**
     * Get Monitor By Id
     */
    @GetMapping("/{monitorId}")
    public ResponseEntity<MonitorResponse> getMonitor(
            @PathVariable UUID monitorId) {

        return ResponseEntity.ok(
                monitorService.getMonitor(monitorId));

    }

    /**
     * Get All Monitors
     */
    @GetMapping
    public ResponseEntity<List<MonitorResponse>> getAllMonitors() {

        return ResponseEntity.ok(
                monitorService.getAllMonitors());

    }

    /**
     * Get Monitors By API
     */
    @GetMapping("/api/{apiId}")
    public ResponseEntity<List<MonitorResponse>> getApiMonitors(
            @PathVariable UUID apiId) {

        return ResponseEntity.ok(
                monitorService.getApiMonitors(apiId));

    }

    /**
     * Update Monitor
     */
    @PutMapping("/{monitorId}")
    public ResponseEntity<MonitorResponse> updateMonitor(
            @PathVariable UUID monitorId,
            @Valid @RequestBody UpdateMonitorRequest request) {

        return ResponseEntity.ok(
                monitorService.updateMonitor(
                        monitorId,
                        request));

    }

    /**
     * Delete Monitor
     */
    @DeleteMapping("/{monitorId}")
    public ResponseEntity<Void> deleteMonitor(
            @PathVariable UUID monitorId) {

        monitorService.deleteMonitor(monitorId);

        return ResponseEntity.noContent().build();

    }

    /**
     * Execute Monitor
     */
    @PostMapping("/{monitorId}/execute")
    public ResponseEntity<MonitorExecutionResponse> executeMonitor(
            @PathVariable UUID monitorId) {

        return ResponseEntity.ok(
                monitorService.executeMonitor(monitorId));

    }

    /**
     * Get Execution History
     */
    @GetMapping("/{monitorId}/history")
    public ResponseEntity<List<MonitorExecutionResponse>>
            getExecutionHistory(
                    @PathVariable UUID monitorId) {

        return ResponseEntity.ok(
                monitorService.getExecutionHistory(
                        monitorId));

    }

    /**
     * Enable Monitor
     */
    @PutMapping("/{monitorId}/enable")
    public ResponseEntity<MonitorResponse> enableMonitor(
            @PathVariable UUID monitorId) {

        return ResponseEntity.ok(
                monitorService.enableMonitor(monitorId));

    }

    /**
     * Disable Monitor
     */
    @PutMapping("/{monitorId}/disable")
    public ResponseEntity<MonitorResponse> disableMonitor(
            @PathVariable UUID monitorId) {

        return ResponseEntity.ok(
                monitorService.disableMonitor(monitorId));

    }
   
    @GetMapping("/statistics/{apiId}")
    public ResponseEntity<MonitorStatisticsResponse> getStatistics(
            @PathVariable UUID apiId) {

        return ResponseEntity.ok(
                monitorService.getStatistics(apiId));
    }

}