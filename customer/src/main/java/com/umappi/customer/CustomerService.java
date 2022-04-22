package com.umappi.customer;


import com.umappi.ampq.RabbitMQMessageProducer;
import com.umappi.clients.fraud.FraudCheckResponse;
import com.umappi.clients.fraud.FraudClient;
import com.umappi.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);
        // ToDo: check if customer is fraudster
        // se utiliza eureka server para service discovery
        /* FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()

        );*/

        FraudCheckResponse fraudCheckResponse = fraudClient.checkCustomerFraud(customer.getId());

        if (fraudCheckResponse.isFraudSter()) {
            throw new IllegalStateException("fraudster");
        }

        log.info("Send notification for customer {}", customer.getId());
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .notificationMessage("Customer is no fraudulent")
                .sender("umappi")
                .sentAt(new Date())
                .toCustomerEmail(customer.getEmail())
                .toCustomerId(customer.getId())
                .build();
        /*notificationClient.sendNotification(notificationRequest);*/

        // send notification to RabbitMq queue

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"

        );
    }
}
