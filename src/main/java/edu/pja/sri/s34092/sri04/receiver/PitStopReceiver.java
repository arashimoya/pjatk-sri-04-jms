package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.PitStopMessage;
import edu.pja.sri.s34092.sri04.model.PitStopResponse;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class PitStopReceiver {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(PitStopReceiver.class);
    private final Random random = new Random();

    @JmsListener(destination = JmsConfig.PIT_STOP)
    public void receiveAndRespond(
            @Payload PitStopMessage convertedMessage,
            @Headers MessageHeaders headers,
            Message message
    ) throws JMSException {
        LOGGER.info("Received a pit-stop request message: {}", convertedMessage);
        PitStopResponse response = PitStopResponse.builder()
                .id(PitStopResponse.nextId())
                .correlatedMessageId(convertedMessage.getId())
                .createdAt(LocalDateTime.now())
                .isAccepted(evaluatePitStopRequest())
                .build();

        Destination replyTo = message.getJMSReplyTo();
        jmsTemplate.convertAndSend(replyTo, response);
    }

    private boolean evaluatePitStopRequest(){
        return random.nextBoolean();
    }
}
