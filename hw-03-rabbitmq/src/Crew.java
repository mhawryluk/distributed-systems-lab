import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Crew {

    private final String EXCHANGE_NAME = "exchange2";
    private final Channel channel;
    private final String name;

    public Crew(String name) throws IOException, TimeoutException {
        this.name = name;

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        listenAdmin();
        listenConfirmations();
        makeOrders();
    }

    private void makeOrders() throws IOException {

        while (true) {
            // read order from stdin
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String orderType = br.readLine();

            // break condition
            if ("exit".equals(orderType)) {
                break;
            }

            String key = "order." + orderType;

            // publish
            channel.basicPublish(EXCHANGE_NAME, key, null, name.getBytes(StandardCharsets.UTF_8));
            System.out.println("Placed an order for " + orderType);
        }
    }

    private void listenConfirmations() throws IOException {

        // queue & bind
        String KEY = "confirm." + name;
        String queueName = channel.queueDeclare(KEY, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, KEY);
        channel.basicQos(1);
        System.out.println("created queue: " + queueName);

        // message handling
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String confirmation = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received confirmation: " + confirmation);

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // start listening
        channel.basicConsume(queueName, false, consumer);
    }

    private void listenAdmin() throws IOException {
        // queue & bind
        String queueName = channel.queueDeclare("admin.crews." + name, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "admin.crews");
        channel.queueBind(queueName, EXCHANGE_NAME, "admin.all");
        System.out.println("created queue: " + queueName);

        // message handling
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);

                System.out.println("Received a message from admin: " + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // start listening
        channel.basicConsume(queueName, false, consumer);
    }

    public static void main(String[] argv) throws Exception {
        // info
        System.out.println("CREW");
        new Crew(argv[0]);
    }
}
