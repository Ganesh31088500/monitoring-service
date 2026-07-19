package com.apiwatch.monitoring_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiwatch.monitoring_service.requests.MonitorExecutionResponse;
import com.apiwatch.monitoring_service.service.MonitorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/monitor-executions")
@RequiredArgsConstructor
public class MonitorExecutionController {

    private final MonitorService monitorService;

    @GetMapping("/recent")
    public ResponseEntity<List<MonitorExecutionResponse>> getRecentExecutions() {

        return ResponseEntity.ok(
                monitorService.getRecentExecutions());

    }
}
