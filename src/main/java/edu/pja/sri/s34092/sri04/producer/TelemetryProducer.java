package edu.pja.sri.s34092.sri04.producer;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.F1TelemetryMessage;
import edu.pja.sri.s34092.sri04.producer.factory.TelemetryMessageFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelemetryProducer {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(TelemetryProducer.class);
    private final TelemetryMessageFactory telemetryMessageFactory = new TelemetryMessageFactory();

    @Scheduled(fixedRate = 10000)
    public void sendTelemetry() {
        F1TelemetryMessage message = telemetryMessageFactory.createRandom();
        LOGGER.info("Sending telemetry: {}", message);
        jmsTemplate.convertAndSend(JmsConfig.TELEMETRY_TOPIC, message);

    }
}
