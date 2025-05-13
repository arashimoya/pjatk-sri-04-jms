package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.F1TelemetryMessage;
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

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class TelemetryRouter {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(TelemetryRouter.class);
    private final static double MAX_ENGINE_TEMP = 110;
    private final static double MIN_TIRE_PRESSURE = 1.8;
    private final static double MIN_OIL_PRESSURE = 2.5;

    @JmsListener(destination = JmsConfig.TELEMETRY_TOPIC, containerFactory = "topicConnectionFactory")
    public void receiveRaceInfo(@Payload F1TelemetryMessage convertedMessage, @Headers MessageHeaders messageHeaders, Message message) {
        boolean critical = false;
        boolean notifyMechanics = false;
        LOGGER.info("Received a message. Checking if any parameters are critical");
        if (isMaxEngineTempExceeded(convertedMessage)) {
            LOGGER.error("ENGINE OVERHEATING: {} C", convertedMessage.getEngineTemperature());
            notifyMechanics = true;
        }

        if (isMinOilPressureExceeded(convertedMessage)) {
            LOGGER.error("LOW OIL PRESSURE: {} bar", convertedMessage.getOilPressure());
            critical = true;
            notifyMechanics = true;
        }

        if (isMinTirePressureExceeded(convertedMessage)) {
            LOGGER.error("LOW TIRE PRESSURE: {} bar", convertedMessage.getTirePressure());
            critical = true;
            notifyMechanics = true;
        }

        if (notifyMechanics) {
            notifyMechanics(convertedMessage);
        }

        if (critical) {
            notifyDriver(convertedMessage);
        }


    }

    private void notifyMechanics(F1TelemetryMessage convertedMessage) {
        jmsTemplate.convertAndSend(JmsConfig.TELEMETRY_MECHANIC_QUEUE, convertedMessage);
    }

    private void notifyDriver(F1TelemetryMessage convertedMessage) {
        jmsTemplate.convertAndSend(JmsConfig.TELEMETRY_DRIVER_QUEUE, convertedMessage);
    }

    private boolean isMaxEngineTempExceeded(F1TelemetryMessage message) {
        return message.getEngineTemperature() > MAX_ENGINE_TEMP;
    }

    private boolean isMinOilPressureExceeded(F1TelemetryMessage message) {
        return message.getOilPressure() < MIN_OIL_PRESSURE;
    }

    private boolean isMinTirePressureExceeded(F1TelemetryMessage message) {
        return Arrays.stream(message.getTirePressure()).filter(pressure -> pressure < MIN_TIRE_PRESSURE).count() > 0;
    }

}
