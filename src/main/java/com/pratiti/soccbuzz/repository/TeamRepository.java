package com.pratiti.soccbuzz.repository;

import com.pratiti.soccbuzz.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity,Integer> {
}
