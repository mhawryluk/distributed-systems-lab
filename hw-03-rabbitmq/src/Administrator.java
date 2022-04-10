import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Administrator {

    private final String EXCHANGE_NAME = "exchange2";
    private final Channel channel;

    public Administrator() throws IOException, TimeoutException {
        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        listenMessages();
        sendMessages();
    }


    private void listenMessages() throws IOException {

        // queue & bind
        String KEY = "#";
        String queueName = channel.queueDeclare(KEY, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, KEY);
        channel.basicQos(1);
        System.out.println("created queue: " + queueName);

        // message handling
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String confirmation = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message: " + confirmation + ", key: " + envelope.getRoutingKey());

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // start listening
        channel.basicConsume(queueName, false, consumer);
    }

    private void sendMessages() throws IOException {

        while (true) {
            // read order from stdin
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String receiver = br.readLine();

            // break condition
            if ("exit".equals(receiver)) {
                break;
            }

            if (!"crews".equals(receiver) && !"suppliers".equals(receiver) && !"all".equals(receiver)){
                System.out.println("Invalid receiver");
                continue;
            }

            String key = "admin." + receiver;
            String message = br.readLine();

            // publish
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent a message: " + message + " to: " + receiver);
        }
    }

    public static void main(String[] argv) throws Exception {
        System.out.println("ADMINISTRATOR");
        new Administrator();
    }
}
