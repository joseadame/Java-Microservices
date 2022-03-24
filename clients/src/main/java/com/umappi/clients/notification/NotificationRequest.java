package com.umappi.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    String notificationMessage;
    String sender;
    LocalDateTime sentAt;
    String toCustomerEmail;
    Integer toCustomerId;
}
