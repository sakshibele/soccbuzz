package com.pratiti.soccbuzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {

    private  Integer matchId;
    @NotNull
    private String matchName;
    private LeagueDTO leagueDTO;
    private TeamDTO team1DTO;
    private TeamDTO team2DTO;
    private ScheduleDTO scheduleDTO;
    private Integer team1Score;
    private Integer team2Score;

}
