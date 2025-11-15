package org.example.rabbitmqwithspringbootdemo.consumer;

import org.example.rabbitmqwithspringbootdemo.dto.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RabbitMQJsonConsumerTest {

    @Test
    void consumeJsonMessage() {
        // Arrange
        RabbitMQJsonConsumer consumer = new RabbitMQJsonConsumer();
        User user = new User(1, "John", "Doe");

        // Act
        consumer.consumeJsonMessage(user);

        // Assert
        // The method currently does nothing, so we just verify it was called without errors
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
    }
}