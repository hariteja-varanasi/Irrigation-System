package com.irrigation.system.repository;

import com.irrigation.system.entity.PlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository<PlotEntity, Integer> {
}
