package com.pratiti.soccbuzz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "Matches")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer matchId;
    private String matchName;

    @ManyToOne
    private LeagueEntity league;

    @ManyToOne
    private TeamEntity team1;

    @ManyToOne
    private TeamEntity team2;


    private Integer team1Score;

    private Integer team2Score;

    @OneToOne
    private ScheduleEntity schedule;

    @Override
    public String toString() {
        return "MatchEntity{" +
                "matchId=" + matchId +
                ", matchName='" + matchName + '\'' +
                ", leagueId=" + (league != null ? league.getLeagueId() : "null") +
                ", team1Id=" + (team1 != null ? team1.getTeamId() : "null") +
                ", team2Id=" + (team2 != null ? team2.getTeamId() : "null") +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId); // Use only the unique identifier
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MatchEntity)) return false;
        MatchEntity other = (MatchEntity) obj;
        return Objects.equals(matchId, other.matchId);
    }
}
