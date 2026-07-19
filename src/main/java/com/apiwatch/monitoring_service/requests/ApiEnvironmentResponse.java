package com.apiwatch.monitoring_service.requests;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiEnvironmentResponse {

    private String environment;

    private String url;

}