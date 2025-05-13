package edu.pja.sri.s34092.sri04.producer.factory;

import edu.pja.sri.s34092.sri04.model.F1TelemetryMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

import static edu.pja.sri.s34092.sri04.model.F1TelemetryMessage.nextId;

@Component
public class TelemetryMessageFactory {

    private final Random random = new Random();

    public F1TelemetryMessage createRandom() {
        return F1TelemetryMessage.builder()
                .id(nextId())
                .createdAt(LocalDateTime.now())
                .engineTemperature(getEngineTemperature())
                .oilPressure(getOilPressure())
                .tirePressure(getTirePressure())
                .speed(getSpeed())
                .fuelLevel(getFuelLevel())
                .lapTime(getLapTime())
                .position(getPosition())
                .build();
    }

    private int getPosition() {
        return random.nextInt(0, 13);
    }

    private double getOilPressure() {
        return 3.0 + random.nextDouble() * 2.0;
    }

    private double[] getTirePressure() {
        return new double[]{
                1.9 + random.nextDouble() * 0.3,
                1.9 + random.nextDouble() * 0.3,
                1.9 + random.nextDouble() * 0.3,
                1.9 + random.nextDouble() * 0.3,
        };
    }

    private double getSpeed() {
        return 250 + random.nextDouble() * 100;
    }

    private double getFuelLevel() {
        return 5 + random.nextDouble() * 130;
    }

    private double getLapTime() {
        return 30 + random.nextDouble() * 30;
    }

    private double getEngineTemperature() {
        return 90 + random.nextDouble() * 30;
    }
}
