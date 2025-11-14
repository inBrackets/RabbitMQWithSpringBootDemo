package org.example.rabbitmqwithspringbootdemo.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RabbitMQProducerTest {

    private RabbitTemplate rabbitTemplate;
    private RabbitMQProducer producer;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        producer = new RabbitMQProducer(rabbitTemplate);

        // Manually inject @Value fields
        ReflectionTestUtils.setField(producer, "exchangeName", "test-exchange");
        ReflectionTestUtils.setField(producer, "routingKeyName", "test-routing-key");
    }

    @Test
    void sendMessage() {
        // Given
        String message = "Hello World";

        // When
        producer.sendMessage(message);

        // Then: verify RabbitTemplate was called correctly
        verify(rabbitTemplate, times(1))
                .convertAndSend("test-exchange", "test-routing-key", message);
    }
}