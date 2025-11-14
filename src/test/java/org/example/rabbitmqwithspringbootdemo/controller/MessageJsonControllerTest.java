package org.example.rabbitmqwithspringbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rabbitmqwithspringbootdemo.dto.User;
import org.example.rabbitmqwithspringbootdemo.publisher.RabbitMQJsonProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageJsonControllerTest {

    private MockMvc mockMvc;
    private RabbitMQJsonProducer jsonProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        jsonProducer = Mockito.mock(RabbitMQJsonProducer.class);
        MessageJsonController controller = new MessageJsonController(jsonProducer);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void sendJsonMessage() throws Exception {
        // Arrange
        User user = new User(1, "John", "Doe");

        // Act & Assert
        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("Json message sent to RabbitMQ ..."));

        // Verify producer was called with correct user
        verify(jsonProducer, times(1)).sendJsonMessage(user);
    }
}