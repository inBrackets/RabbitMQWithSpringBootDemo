package org.example.rabbitmqwithspringbootdemo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    @Value("${spring.rabbitmq.queue.json.name}")
    private String jsonQueueName;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing.key.name}")
    private String routingKeyName;

    @Value("${spring.rabbitmq.routing.json.key.name}")
    private String jsonRoutingKeyName;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    // spring bean for rabbitmq queue (store json messages)
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueueName);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(routingKeyName);
    }

    // binding between json queue and exchange using routing key
    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(topicExchange())
                .with(jsonRoutingKeyName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}
