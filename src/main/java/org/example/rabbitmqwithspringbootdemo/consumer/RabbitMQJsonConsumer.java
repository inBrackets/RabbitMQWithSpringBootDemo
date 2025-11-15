package org.example.rabbitmqwithspringbootdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmqwithspringbootdemo.dto.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonConsumer {

    @RabbitListener(queues = "${spring.rabbitmq.queue.json.name}")
    public void consumeJsonMessage(User user) {
        log.info("Received JSON message -> {}", user.toString());
    }

}
