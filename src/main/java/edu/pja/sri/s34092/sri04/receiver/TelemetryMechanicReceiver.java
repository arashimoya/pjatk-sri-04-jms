package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.model.F1TelemetryMessage;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static edu.pja.sri.s34092.sri04.config.JmsConfig.TELEMETRY_MECHANIC_QUEUE;

@Component
public class TelemetryMechanicReceiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(TelemetryMechanicReceiver.class);

    @JmsListener(destination = TELEMETRY_MECHANIC_QUEUE, containerFactory = "queueConnectionFactory")
    public void receiveHelloMessage(
            @Payload F1TelemetryMessage convertedMessage,
            @Headers MessageHeaders headers,
            Message message
    ) {
        LOGGER.info("Mechanics received a message: {}", convertedMessage);
    }
}
