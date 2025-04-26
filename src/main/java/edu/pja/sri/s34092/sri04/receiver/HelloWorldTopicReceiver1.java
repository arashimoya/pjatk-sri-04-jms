package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.HelloMessage;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldTopicReceiver1 {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldTopicReceiver1.class);

    @JmsListener(destination = JmsConfig.TOPIC_HELLO_WORLD,
            containerFactory = "topicConnectionFactory")
    public void receiveHelloMessage(
            @Payload HelloMessage convertedMessage,
            @Headers MessageHeaders messageHeaders,
            Message message
    ) {
        LOGGER.info("Received a message: {}", convertedMessage);
    }
}
