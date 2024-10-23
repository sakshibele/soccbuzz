package com.pratiti.soccbuzz.repository;

import com.pratiti.soccbuzz.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
    @Query("SELECT m FROM MatchEntity m WHERE m.team1.teamName = :teamName OR m.team2.teamName = :teamName")
    List<MatchEntity> findByTeamName(@Param("teamName") String teamName);

    // Custom query to find matches by match date
    @Query("SELECT m FROM MatchEntity m WHERE m.schedule.matchDateTime = :date")
    List<MatchEntity> findByMatchDate(@Param("date") LocalDateTime date);

    @Query("SELECT m FROM MatchEntity m WHERE (m.team1.teamName = :teamName OR m.team2.teamName = :teamName) AND m.schedule.matchDateTime BETWEEN :startDate AND :endDate")
    List<MatchEntity> findByTeamNameAndMatchDateBetween(@Param("teamName") String teamName,@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

}
