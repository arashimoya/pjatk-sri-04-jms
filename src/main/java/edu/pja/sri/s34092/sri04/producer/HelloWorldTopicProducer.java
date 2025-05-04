package edu.pja.sri.s34092.sri04.producer;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HelloWorldTopicProducer {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldTopicProducer.class);

//    @Scheduled(fixedRate = 2500)
    public void sendHello() {
        HelloMessage message = HelloMessage.builder()
                .id(HelloMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("Hello world!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.TOPIC_HELLO_WORLD, message);
        LOGGER.info("Sent message: {}", message);
    }
}
