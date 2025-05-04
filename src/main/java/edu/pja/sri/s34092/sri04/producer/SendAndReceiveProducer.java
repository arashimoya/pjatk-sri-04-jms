package edu.pja.sri.s34092.sri04.producer;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.HelloMessage;
import edu.pja.sri.s34092.sri04.model.HelloResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SendAndReceiveProducer {

    private final JmsTemplate jmsTemplate;
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(SendAndReceiveProducer.class);

//    @Scheduled(fixedRate = 5000)
    public void sendAndReceive() {
        HelloMessage message = HelloMessage.builder()
                .id(HelloMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("Thank you!")
                .build();

        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);

        LOGGER.info("I'm about to send a message: {}", message);

        HelloResponse response = jmsMessagingTemplate.convertSendAndReceive(
                JmsConfig.QUEUE_SEND_AND_RECEIVE,
                message,
                HelloResponse.class
        );
        Optional<String> responseTextOpt = Optional.of(response).map(HelloResponse::getMessage);

        LOGGER.info("I've received a response: {} \tconvertedMessage: {}", responseTextOpt.orElse("null"), response);
    }

}
