package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.model.HelloMessage;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static edu.pja.sri.s34092.sri04.config.JmsConfig.QUEUE_HELLO_WORLD;

@Component
public class HelloWorldQueueReceiver2 {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldQueueReceiver2.class);

    @JmsListener(destination = QUEUE_HELLO_WORLD, containerFactory = "queueConnectionFactory")
    public void receiveHelloMessage(
            @Payload HelloMessage convertedMessage,
            @Headers MessageHeaders headers,
            Message message
    ) {
        LOGGER.info("Received a message: {}", convertedMessage);
    }
}
