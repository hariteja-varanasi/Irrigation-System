package com.irrigation.system.repository;

import com.irrigation.system.entity.IrrigationSystemHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationSystemHistoryRepository extends JpaRepository<IrrigationSystemHistoryEntity, Integer> {
}
