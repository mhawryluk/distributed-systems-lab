import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;


public class Supplier {

    private final Channel channel;
    private final int[] orderCount = {0};
    private final String EXCHANGE_NAME = "exchange2";
    private final String name;


    public Supplier(String name, Set <String> gear) throws IOException, TimeoutException {
        this.name = name;

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // queues & message handlers
        for (String orderType : gear){
            handleOrders(orderType);
        }

    }

    private void handleOrders(String orderType) throws IOException {

        // queue & bind
        String KEY = "order." + orderType;
        String queueName = channel.queueDeclare(KEY, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, KEY);
        channel.basicQos(1);
        System.out.println("created queue: " + queueName);

        // message handling
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                orderCount[0]++;
                String crewName = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received an order for " + orderType + " order #" + orderCount[0] + " from " + crewName);

                channel.basicAck(envelope.getDeliveryTag(), false);

                String KEY = "confirm." + crewName;
                channel.basicPublish(EXCHANGE_NAME, KEY, null, ("supplier: " + name + ", orderID: " + orderCount[0] + ", orderType: " + orderType + ", crewName: " + crewName).getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent confirmation to " + crewName + " (" + orderType + ")");
            }
        };

        // start listening
        channel.basicConsume(queueName, false, consumer);
    }

    public static void main(String[] argv) throws Exception {
        System.out.println("SUPPLIER");
        Set<String> gear = new HashSet<>(Arrays.asList(argv).subList(1, argv.length));
        Supplier supplier = new Supplier(argv[0], gear);
    }
}
