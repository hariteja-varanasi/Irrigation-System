package com.irrigation.system.repository;

import com.irrigation.system.entity.IrrigationSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationSystemRepository extends JpaRepository<IrrigationSystemEntity, Integer> {
}
