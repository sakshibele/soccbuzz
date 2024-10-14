package com.pratiti.soccbuzz.repository;

import com.pratiti.soccbuzz.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
}
