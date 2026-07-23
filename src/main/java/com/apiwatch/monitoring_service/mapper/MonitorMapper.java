package com.apiwatch.monitoring_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.apiwatch.monitoring_service.entity.Monitor;
import com.apiwatch.monitoring_service.entity.MonitorExecution;
import com.apiwatch.monitoring_service.requests.CreateMonitorRequest;
import com.apiwatch.monitoring_service.requests.MonitorExecutionResponse;
import com.apiwatch.monitoring_service.requests.MonitorResponse;



@Mapper(componentModel = "spring")
public interface MonitorMapper {

    /**
     * Request -> Entity
     */
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "enabled", constant = "true")

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Monitor toEntity(CreateMonitorRequest request);

    /**
     * Entity -> Response
     */
    MonitorResponse toResponse(
            Monitor monitor
    );

    /**
     * Execution -> Response
     */
    @Mapping(
            target = "monitorId",
            source = "monitor.id"
    )
    @Mapping(target = "monitorName",
    source = "monitor.name")
    MonitorExecutionResponse toExecutionResponse(
            MonitorExecution execution
    );

}