package edu.pja.sri.s34092.sri04.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class F1TelemetryMessage {

    private static long idIndex = 0;
    public static long nextId() {
        return idIndex++;
    }
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private long id;
    private double engineTemperature;
    private double oilPressure;
    private double[] tirePressure;
    private double speed;
    private double fuelLevel;
    private double lapTime;
    private int position;

}
