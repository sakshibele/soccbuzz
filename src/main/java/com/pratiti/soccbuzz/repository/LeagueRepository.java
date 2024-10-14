package com.pratiti.soccbuzz.repository;

import com.pratiti.soccbuzz.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<LeagueEntity,Integer> {
}
