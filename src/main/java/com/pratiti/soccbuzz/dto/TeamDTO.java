package com.pratiti.soccbuzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Integer teamId;
    private  String teamName;
    private LeagueDTO leagueDTO;
}
