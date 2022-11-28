package com.irrigation.system.repository;

import com.irrigation.system.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {

    @Query(value = "SELECT se FROM StatusEntity se WHERE se.description=:description")
    StatusEntity getStatusEntityByDesc(@Param("description") String description);

}
