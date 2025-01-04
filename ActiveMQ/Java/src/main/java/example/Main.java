package example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Main {
    private void produce() throws Exception {
        // Create a connection factory.
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "");

        // Pass the sign-in credentials.
        connectionFactory.setUserName("AmazonMqUsername");
        connectionFactory.setPassword("AmazonMqPassword");

        // Create a pooled connection factory.
        final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(10);

        // Establish a connection for the producer.
        final Connection producerConnection = pooledConnectionFactory.createConnection();
        producerConnection.start();

        // Create a session.
        final Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a queue named "MyQueue".
        final Destination producerDestination = producerSession.createQueue("MyQueue");

        // Create a producer from the session to the queue.
        final MessageProducer producer = producerSession.createProducer(producerDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        try {
            // Create a message.
            final String text = "Hello from Amazon MQ!";
            TextMessage producerMessage = producerSession.createTextMessage(text);

            // Send the message.
            producer.send(producerMessage);
            System.out.println("Message sent.");

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            producer.close();
            producerSession.close();
            producerConnection.close();

            // Close all connections in the pool.
            pooledConnectionFactory.clear();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().produce();
    }
}