package com.pratiti.soccbuzz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "League")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leagueId;
    private String leagueName;

    @OneToMany(mappedBy = "league")
    private Set<MatchEntity> matches;

    @OneToMany(mappedBy = "league")
    private Set<TeamEntity> teams;

    @Override
    public String toString() {
        return "LeagueEntity{" +
                "leagueId=" + leagueId +
                ", leagueName='" + leagueName + '\'' +
                '}';
    }
}
