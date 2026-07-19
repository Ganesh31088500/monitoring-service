package com.apiwatch.monitoring_service.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apiwatch.monitoring_service.entity.Monitor;
import com.apiwatch.monitoring_service.repository.MonitorRepository;
import com.apiwatch.monitoring_service.service.MonitorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonitorScheduler {

    private final MonitorRepository monitorRepository;

    private final MonitorService monitorService;

    /**
     * Runs every minute
     */
    @Scheduled(fixedRate = 60000)
    public void executeMonitors() {

        log.info("Starting Scheduled Monitor Execution...");

        List<Monitor> monitors =
                monitorRepository.findByEnabledTrue();

        if (monitors.isEmpty()) {

            log.info("No enabled monitors found.");

            return;

        }

        for (Monitor monitor : monitors) {

            try {

                log.info("Executing Monitor : {}",
                        monitor.getName());

                monitorService.executeMonitor(
                        monitor.getId());

            } catch (Exception ex) {

                log.error(
                        "Failed to execute monitor {} : {}",
                        monitor.getName(),
                        ex.getMessage());

            }

        }

        log.info("Scheduled Monitor Execution Completed.");

    }

}