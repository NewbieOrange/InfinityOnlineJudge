package xyz.chengzi.ooad.service;

import com.rabbitmq.client.*;

import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class RabbitMQService {
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection;

    public RabbitMQService() {
        try {
            connectionFactory.setUri("amqp://sender:123456@10.20.104.26:5672/my_vhost");
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message, Consumer<String> callback) {
        try {
            Channel channel = connection.createChannel();
            final String corrId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().correlationId(corrId)
                    .replyTo(replyQueueName).build();
            channel.basicPublish("", "ioj-judger", basicProperties, message.getBytes());
            channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    String payload = new String(delivery.getBody());
                    if (payload.isEmpty()) {
                        channel.basicCancel(consumerTag);
                        try {
                            channel.close();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    callback.accept(payload);
                }
            }, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
