package com.apiwatch.monitoring_service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.apiwatch.monitoring_service.config.FeignExceptionConfig;
import com.apiwatch.monitoring_service.requests.ApiDetailsResponse;


@FeignClient(
        name = "API-SERVICE",
        configuration = FeignExceptionConfig.class
)
public interface ApiServiceClient {

    /**
     * Existing API endpoint
     */
    @GetMapping("/api/v1/apis/{apiId}")
    ApiResponse getApi(
            @PathVariable UUID apiId);

    /**
     * Complete API Details
     */
    @GetMapping("/api/v1/apis/{apiId}/details")
    ApiDetailsResponse getApiDetails(
            @PathVariable UUID apiId);

}