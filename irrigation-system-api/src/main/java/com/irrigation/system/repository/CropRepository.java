package com.irrigation.system.repository;


import com.irrigation.system.entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<CropEntity, Integer> {

    List<CropEntity> findByPlotId(Integer plotId);

}
