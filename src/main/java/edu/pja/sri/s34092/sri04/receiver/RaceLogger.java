package edu.pja.sri.s34092.sri04.receiver;

import edu.pja.sri.s34092.sri04.config.JmsConfig;
import edu.pja.sri.s34092.sri04.model.F1TelemetryMessage;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RaceLogger {

    private final static Logger LOGGER = LoggerFactory.getLogger(RaceLogger.class);

    @JmsListener(destination = JmsConfig.TELEMETRY_TOPIC, containerFactory = "topicConnectionFactory")
    public void receiveRaceInfo(@Payload F1TelemetryMessage convertedMessage, @Headers MessageHeaders messageHeaders, Message message) {
        LOGGER.info("""
                        Received telemetry:
                        ID: {}
                        Time: {}
                        Engine Temp: {}°C
                        Oil Pressure: {} bar
                        Tire Pressures: [{}, {}, {}, {}] bar
                        Speed: {} km/h
                        Fuel: {} L
                        Lap Time: {}s
                        Position: {}""",
                convertedMessage.getId(),
                convertedMessage.getCreatedAt(),
                convertedMessage.getEngineTemperature(),
                convertedMessage.getOilPressure(),
                convertedMessage.getTirePressure()[0],
                convertedMessage.getTirePressure()[1],
                convertedMessage.getTirePressure()[2],
                convertedMessage.getTirePressure()[3],
                convertedMessage.getSpeed(),
                convertedMessage.getFuelLevel(),
                convertedMessage.getLapTime(),
                convertedMessage.getPosition()
        );

    }
}
