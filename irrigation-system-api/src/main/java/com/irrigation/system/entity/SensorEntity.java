package com.irrigation.system.entity;


import javax.persistence.*;

@Entity
public class SensorEntity {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "status_id")
    private StatusEntity statusEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

}
