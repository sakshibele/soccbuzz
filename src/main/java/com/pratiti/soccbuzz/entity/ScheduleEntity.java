package com.pratiti.soccbuzz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

//    private String matchDateTime;
    private LocalDateTime matchDateTime;

//    @OneToOne(mappedBy = "schedule")
//    private MatchEntity match;

    // Optional: Add a method to format the date and time for display
    public String getFormattedMatchDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return matchDateTime.format(formatter);
    }
}
