package org.example.rabbitmqwithspringbootdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    public void consume(String message) {
        log.info("Receive message -> {}", message);
    }
}
