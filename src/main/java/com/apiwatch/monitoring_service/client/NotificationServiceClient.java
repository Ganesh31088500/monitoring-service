package com.apiwatch.monitoring_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apiwatch.monitoring_service.config.FeignExceptionConfig;



@FeignClient(
        name = "NOTIFICATION-SERVICE",
        configuration = FeignExceptionConfig.class
)
public interface NotificationServiceClient {

    @PostMapping("/api/v1/notifications/send")
    void sendNotification(
            @RequestBody
            SendNotificationRequest request);

}
