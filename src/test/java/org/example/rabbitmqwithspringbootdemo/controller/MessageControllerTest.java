package org.example.rabbitmqwithspringbootdemo.controller;

import org.example.rabbitmqwithspringbootdemo.publisher.RabbitMQProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest {

    private MockMvc mockMvc;
    private RabbitMQProducer producer;

    @BeforeEach
    void setup() {
        producer = Mockito.mock(RabbitMQProducer.class);
        MessageController controller = new MessageController(producer);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void sendMessage() throws Exception {
        String message = "Hello World";

        mockMvc.perform(get("/api/v1/publish")
                        .param("message", message)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent to RabbitMQ ..."));

        // Verify that producer.sendMessage() was called once with the correct message
        verify(producer, times(1)).sendMessage(message);
    }
}