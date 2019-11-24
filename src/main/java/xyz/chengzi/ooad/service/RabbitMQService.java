package xyz.chengzi.ooad.service;

import com.rabbitmq.client.*;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

    public String send(String message) {
        try (Channel channel = connection.createChannel()) {
            final String corrId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().correlationId(corrId)
                    .replyTo(replyQueueName).build();
            channel.basicPublish("", "ioj-judger", basicProperties, message.getBytes());
            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody()));
                }
            }, consumerTag -> {
            });
            String result = response.take();
            channel.basicCancel(ctag);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
