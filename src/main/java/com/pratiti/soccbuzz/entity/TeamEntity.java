package com.pratiti.soccbuzz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;
    private  String teamName;

    @OneToMany(mappedBy = "team1")
    private Set<MatchEntity> team_1_matches;

    @OneToMany(mappedBy = "team2")
    private Set<MatchEntity> team_2_matches;

    @ManyToOne
    private LeagueEntity league;
}
