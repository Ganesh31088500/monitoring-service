package com.apiwatch.monitoring_service.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiHeaderResponse {

    private String headerKey;

    private String headerValue;

}