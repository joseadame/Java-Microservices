package com.umappi.clients.notification;

import com.umappi.clients.fraud.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification")
public interface NotificationClient {
    @PostMapping(path ="api/v1/notification")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
