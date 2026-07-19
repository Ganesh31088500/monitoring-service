package com.apiwatch.monitoring_service.service;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiwatch.monitoring_service.client.ApiResponse;
import com.apiwatch.monitoring_service.client.ApiServiceClient;
import com.apiwatch.monitoring_service.client.NotificationServiceClient;
import com.apiwatch.monitoring_service.client.SendNotificationRequest;
import com.apiwatch.monitoring_service.entity.Monitor;
import com.apiwatch.monitoring_service.entity.MonitorExecution;
import com.apiwatch.monitoring_service.entity.MonitorStatisticsResponse;
import com.apiwatch.monitoring_service.enums.MonitorStatus;
import com.apiwatch.monitoring_service.mapper.MonitorMapper;
import com.apiwatch.monitoring_service.repository.MonitorExecutionRepository;
import com.apiwatch.monitoring_service.repository.MonitorRepository;
import com.apiwatch.monitoring_service.requests.ApiDetailsResponse;
import com.apiwatch.monitoring_service.requests.CreateMonitorRequest;
import com.apiwatch.monitoring_service.requests.MonitorExecutionResponse;
import com.apiwatch.monitoring_service.requests.MonitorResponse;
import com.apiwatch.monitoring_service.requests.UpdateMonitorRequest;


import com.apiwatch.monitoring_service.exceptions.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MonitorServiceImpl implements MonitorService {

    /**
     * Repository
     */
    private final MonitorRepository monitorRepository;

    private final MonitorExecutionRepository monitorExecutionRepository;

    /**
     * Mapper
     */
    private final MonitorMapper monitorMapper;

    /**
     * Feign Client
     */
    private final ApiServiceClient apiServiceClient;

    /**
     * HTTP Client
     */
    private final RestClient restClient;
    private final NotificationServiceClient
    notificationServiceClient;
    @Override
    public MonitorResponse createMonitor(
            CreateMonitorRequest request) {

        log.info("Creating Monitor : {}", request.getName());

        /*
         * Verify API Exists
         */
        try {

            ApiResponse api =
                    apiServiceClient.getApi(
                            request.getApiId());

            log.info("API : {}", api);

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;
        }

        /*
         * Check Duplicate Monitor Name
         */
        if (monitorRepository.existsByApiIdAndName(
                request.getApiId(),
                request.getName())) {

            throw new MonitorAlreadyExistsException(
                    "Monitor already exists.");

        }

        Monitor monitor =
                monitorMapper.toEntity(request);

        monitor.setEnabled(true);

        Monitor savedMonitor =
                monitorRepository.save(monitor);

        log.info("Monitor Created Successfully : {}",
                savedMonitor.getId());

        return monitorMapper.toResponse(savedMonitor);

    }
    @Override
    @Transactional(readOnly = true)
    public MonitorResponse getMonitor(
            UUID monitorId) {

        log.info("Fetching Monitor : {}", monitorId);

        Monitor monitor =
                monitorRepository.findById(monitorId)

                        .orElseThrow(() ->
                                new MonitorNotFoundException(
                                        "Monitor not found."));

        return monitorMapper.toResponse(monitor);

    }
    @Override
    @Transactional(readOnly = true)
    public List<MonitorResponse> getAllMonitors() {

        log.info("Fetching all monitors");

        return monitorRepository.findAll()

                .stream()

                .map(monitorMapper::toResponse)

                .toList();

    }
    @Override
    @Transactional(readOnly = true)
    public List<MonitorResponse> getApiMonitors(
            UUID apiId) {

        log.info("Fetching monitors for API : {}", apiId);

        /*
         * Verify API Exists
         */
        try {

            apiServiceClient.getApi(apiId);

        } catch (Exception ex) {

            throw new MonitorNotFoundException(
                    "API does not exist.");

        }

        return monitorRepository.findByApiId(apiId)

                .stream()

                .map(monitorMapper::toResponse)

                .toList();

    }
    @Override
    public MonitorResponse updateMonitor(
            UUID monitorId,
            UpdateMonitorRequest request) {

        log.info("Updating Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        if (request.getName() != null &&
                !request.getName().equals(monitor.getName())) {

            if (monitorRepository.existsByApiIdAndName(
                    monitor.getApiId(),
                    request.getName())) {

                throw new MonitorAlreadyExistsException(
                        "Monitor name already exists.");

            }

            monitor.setName(request.getName());

        }

        if (request.getMonitorType() != null) {

            monitor.setMonitorType(
                    request.getMonitorType());

        }

        if (request.getInterval() != null) {

            monitor.setInterval(
                    request.getInterval());

        }

        if (request.getTimeoutSeconds() != null) {

            monitor.setTimeoutSeconds(
                    request.getTimeoutSeconds());

        }

        if (request.getExpectedStatus() != null) {

            monitor.setExpectedStatus(
                    request.getExpectedStatus());

        }

        if (request.getExpectedResponse() != null) {

            monitor.setExpectedResponse(
                    request.getExpectedResponse());

        }

        if (request.getEnabled() != null) {

            monitor.setEnabled(
                    request.getEnabled());

        }

        Monitor updatedMonitor =
                monitorRepository.save(monitor);

        log.info("Monitor updated successfully : {}",
                updatedMonitor.getId());

        return monitorMapper.toResponse(updatedMonitor);

    }
    @Override
    public void deleteMonitor(
            UUID monitorId) {

        log.info("Deleting Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        /*
         * Delete execution history first
         */
        monitorExecutionRepository.deleteByMonitor(
                monitor);

        /*
         * Delete monitor
         */
        monitorRepository.delete(monitor);

        log.info("Monitor deleted successfully : {}",
                monitorId);

    }
    @Override
    public MonitorStatisticsResponse getStatistics(UUID apiId) {

        Double average =
                monitorExecutionRepository.getAverageResponseTime(apiId);
        List<MonitorExecution> executions =
                monitorExecutionRepository.findLatestExecution(apiId);

        MonitorExecution latest =
                executions.isEmpty() ? null : executions.get(0);

        long success = monitorExecutionRepository.countByApiIdAndStatus(
                apiId,
                MonitorStatus.SUCCESS);

        long failure = monitorExecutionRepository.countByApiIdAndStatus(
                apiId,
                MonitorStatus.FAILED);

        return MonitorStatisticsResponse.builder()

                .averageResponseTime(
                        latest == null
                                ? 0L
                                : Math.round(average))

                .lastExecution(
                		latest == null  //latest cannot be resolved to a variable
                                ? null
                                : latest.getExecutedAt())

                .successCount(success)
                .failureCount(failure)
                
                .build();
    }
    @Override
    public MonitorExecutionResponse executeMonitor(
            UUID monitorId) {

        log.info("Executing Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        /*
         * Get API Details
         */
        ApiResponse api;

        try {

            api = apiServiceClient.getApi(
                    monitor.getApiId());

        } catch (Exception ex) {

            throw new MonitorExecutionException(
                    "Unable to fetch API details.");

        }

        long startTime = System.currentTimeMillis();

        MonitorExecution execution = new MonitorExecution();

        execution.setMonitor(monitor);

        execution.setExecutedAt(LocalDateTime.now());

        try {

            ResponseEntity<String> response =
                    executeHttpRequest(api);

            long responseTime =
                    System.currentTimeMillis() - startTime;

            execution.setHttpStatus(
                    response.getStatusCode().value());

            execution.setResponseTime(
                    responseTime);

            execution.setResponseBody(
                    response.getBody());

            /*
             * Validate Expected Status
             */
            if (response.getStatusCode().value()
                    == monitor.getExpectedStatus()) {

                execution.setStatus(
                        MonitorStatus.SUCCESS);

            } else {

                execution.setStatus(
                        MonitorStatus.FAILED);

            }

        } catch (Exception ex) {

            execution.setStatus(
                    MonitorStatus.FAILED);

            execution.setErrorMessage(
                    ex.getMessage());

        }

        /*
         * Save Execution
         */
        MonitorExecution savedExecution =
                monitorExecutionRepository.save(execution);

        /*
         * Send Notification if Monitor Failed
         */
        if (savedExecution.getStatus() == MonitorStatus.FAILED) {

            try {

                notificationServiceClient.sendNotification(

                        SendNotificationRequest.builder()

                                .monitorId(monitor.getId())

                                .subject("🚨 API DOWN ALERT")

                                .message(
                                        String.format(
                                                """
                                                Monitor Name : %s

                                                API Name : %s

                                                API URL : %s

                                                Expected Status : %d

                                                Actual Status : %d

                                                Response Time : %d ms

                                                Executed At : %s

                                                Error : %s
                                                """,

                                                monitor.getName(),

                                                api.getName(),

                                                api.getBaseUrl(),

                                                monitor.getExpectedStatus(),

                                                savedExecution.getHttpStatus(),

                                                savedExecution.getResponseTime(),

                                                savedExecution.getExecutedAt(),

                                                savedExecution.getErrorMessage() == null
                                                        ? "N/A"
                                                        : savedExecution.getErrorMessage()
                                        ))

                                .build());

                log.info("Failure notification sent successfully.");

            } catch (Exception ex) {

                log.error("Unable to send notification : {}",
                        ex.getMessage());

            }

        }

        log.info("Monitor executed successfully.");

        return monitorMapper.toExecutionResponse(
                savedExecution);

    }
    private ResponseEntity<String> executeHttpRequest(
            ApiResponse api) {

        URI uri = UriComponentsBuilder

                .fromHttpUrl(api.getBaseUrl())

                .build()

                .toUri();

        switch (api.getMethod().toUpperCase()) {

            case "GET":

                return restClient.get()

                        .uri(uri)

                        .retrieve()

                        .toEntity(String.class);

            case "POST":

                return restClient.post()

                        .uri(uri)

                        .retrieve()

                        .toEntity(String.class);

            case "PUT":

                return restClient.put()

                        .uri(uri)

                        .retrieve()

                        .toEntity(String.class);

            case "DELETE":

                return restClient.delete()

                        .uri(uri)

                        .retrieve()

                        .toEntity(String.class);

            default:

                throw new MonitorExecutionException(
                        "Unsupported HTTP Method");

        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<MonitorExecutionResponse> getExecutionHistory(
            UUID monitorId) {

        log.info("Fetching execution history for Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        return monitorExecutionRepository
                .findByMonitorOrderByExecutedAtDesc(monitor)

                .stream()

                .map(monitorMapper::toExecutionResponse)

                .toList();

    }
    @Override
    public MonitorResponse enableMonitor(
            UUID monitorId) {

        log.info("Enabling Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        if (Boolean.TRUE.equals(monitor.getEnabled())) {

            log.info("Monitor already enabled.");

            return monitorMapper.toResponse(monitor);

        }

        monitor.setEnabled(true);

        Monitor updatedMonitor =
                monitorRepository.save(monitor);

        log.info("Monitor enabled successfully.");

        return monitorMapper.toResponse(updatedMonitor);

    }
    @Override
    public MonitorResponse disableMonitor(
            UUID monitorId) {

        log.info("Disabling Monitor : {}", monitorId);

        Monitor monitor = monitorRepository.findById(monitorId)

                .orElseThrow(() ->
                        new MonitorNotFoundException(
                                "Monitor not found."));

        if (Boolean.FALSE.equals(monitor.getEnabled())) {

            log.info("Monitor already disabled.");

            return monitorMapper.toResponse(monitor);

        }

        monitor.setEnabled(false);

        Monitor updatedMonitor =
                monitorRepository.save(monitor);

        log.info("Monitor disabled successfully.");

        return monitorMapper.toResponse(updatedMonitor);

    }
    @Override
    public List<MonitorExecutionResponse> getRecentExecutions() {

        return monitorExecutionRepository

                .findTop10ByOrderByExecutedAtDesc()

                .stream()

                .map(monitorMapper::toExecutionResponse)

                .toList();
    }
    
}