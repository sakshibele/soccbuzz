package com.pratiti.soccbuzz.repository;

import com.pratiti.soccbuzz.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Integer> {
}
