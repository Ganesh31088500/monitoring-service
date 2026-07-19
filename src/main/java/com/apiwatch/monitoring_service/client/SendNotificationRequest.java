package com.apiwatch.monitoring_service.client;


import java.util.UUID;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {

    private UUID monitorId;

    private String subject;

    private String message;

}