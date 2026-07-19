package com.apiwatch.monitoring_service.requests;

import java.util.List;
import java.util.UUID;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiDetailsResponse {

    private UUID id;

    private String baseUrl;

    private String method;

    private String protocol;

    private String authenticationType;

    private List<ApiHeaderResponse> headers;

    private List<ApiQueryParamResponse> queryParams;

    private List<ApiEnvironmentResponse> environments;

}