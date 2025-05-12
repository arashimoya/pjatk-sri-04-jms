package edu.pja.sri.s34092.sri04.producer;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.PitStopMessage;
import edu.pja.sri.s34092.sri04.model.PitStopResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class PitStopNeedProducer {

    private final JmsTemplate jmsTemplate;
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(PitStopNeedProducer.class);
    private final Random random = new Random();

    @Scheduled(fixedRate = 2000)
    public void sendAndReceive() {
        if(!shouldSendMessage()){
            return;
        }

        PitStopMessage message =  PitStopMessage.builder()
                .id(PitStopMessage.nextId())
                .createdAt(LocalDateTime.now())
                .build();

        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);
        LOGGER.info("Sending pit-stop request: {}", message);

        PitStopResponse response = jmsMessagingTemplate.convertSendAndReceive(
                JmsConfig.PIT_STOP,
                message,
                PitStopResponse.class
        );
        Boolean isAccepted = response.getIsAccepted();

        if(isAccepted)
            LOGGER.info("Received a response: {} \n GOING FOR A PIT-STOP.", response);
        else
            LOGGER.info("Received a response: {} \n Continuing to drive.", response);



    }

    private Boolean shouldSendMessage(){
        return random.nextInt(3) == 0;
    }
}
