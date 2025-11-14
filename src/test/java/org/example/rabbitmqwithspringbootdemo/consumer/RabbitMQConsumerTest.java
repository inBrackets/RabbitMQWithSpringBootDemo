package org.example.rabbitmqwithspringbootdemo.consumer;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConsumerTest {

    @Test
    void consume() {
        // Arrange
        RabbitMQConsumer consumer = new RabbitMQConsumer();
        LogCaptor logCaptor = LogCaptor.forClass(RabbitMQConsumer.class);

        String testMessage = "Hello RabbitMQ";

        // Act
        consumer.consume(testMessage);

        // Assert
        assertTrue(
                logCaptor.getInfoLogs().stream()
                        .anyMatch(log -> log.contains("Receive message -> " + testMessage)),
                "Expected log entry not found"
        );
    }
}