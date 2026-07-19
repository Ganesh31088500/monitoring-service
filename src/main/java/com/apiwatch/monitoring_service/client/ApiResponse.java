package com.apiwatch.monitoring_service.client;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private UUID id;

    private UUID projectId;

    private String name;

    private String slug;

    private String description;

    private String baseUrl;

    private String method;

    private String protocol;

    private String authenticationType;

    private String status;

}
