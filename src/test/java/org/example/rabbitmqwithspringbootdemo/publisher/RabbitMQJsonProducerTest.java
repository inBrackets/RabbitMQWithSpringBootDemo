package org.example.rabbitmqwithspringbootdemo.publisher;

import org.example.rabbitmqwithspringbootdemo.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RabbitMQJsonProducerTest {

    private RabbitTemplate rabbitTemplate;
    private RabbitMQJsonProducer jsonProducer;

    @BeforeEach
    void setUp() {
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        jsonProducer = new RabbitMQJsonProducer(rabbitTemplate);

        // Inject @Value private fields
        ReflectionTestUtils.setField(jsonProducer, "exchangeName", "test-json-exchange");
        ReflectionTestUtils.setField(jsonProducer, "routingJsonKey", "test-json-routing");
    }

    @Test
    void sendJsonMessage() {
        // Arrange
        User user = new User(1, "John", "Doe");

        // Act
        jsonProducer.sendJsonMessage(user);

        // Capture the user argument
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Assert
        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("test-json-exchange"), eq("test-json-routing"), userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        // Assert the body contains correct name
        assertThat(capturedUser.getFirstName()).isEqualTo("John");
        assertThat(capturedUser.getLastName()).isEqualTo("Doe");
    }
}