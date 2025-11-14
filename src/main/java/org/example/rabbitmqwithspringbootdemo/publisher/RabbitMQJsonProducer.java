package org.example.rabbitmqwithspringbootdemo.publisher;

import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmqwithspringbootdemo.dto.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonProducer {

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing.json.key.name}")
    private String routingJsonKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(User user) {
        log.info("Json message sent -> {}", user.toString());
        rabbitTemplate.convertAndSend(exchangeName, routingJsonKey, user);
    }
}
