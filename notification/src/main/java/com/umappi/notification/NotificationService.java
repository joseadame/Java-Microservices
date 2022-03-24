package com.umappi.notification;

import com.umappi.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public void sendNotification(NotificationRequest notificationRequest) {
        // todo: send notification

        Notification notification = Notification.builder()
                .notificationMessage(notificationRequest.getNotificationMessage())
                .sender(notificationRequest.getSender())
                .toCustomerEmail(notificationRequest.getToCustomerEmail())
                .sentAt(notificationRequest.getSentAt())
                .toCustomerId(notificationRequest.getToCustomerId())
                .build();
        notificationRepository.save(notification);
        log.info("Notification saved");
    }
}
