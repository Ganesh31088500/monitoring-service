package com.apiwatch.monitoring_service.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiQueryParamResponse {

    private String paramKey;

    private String paramValue;

}