package com.irrigation.system.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Irrigation_System_History")
public class IrrigationSystemHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plot_id", referencedColumnName = "id")
    private PlotEntity plot;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private SensorEntity sensor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusEntity status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "system_id", referencedColumnName = "id")
    private IrrigationSystemEntity irrigationSystem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crop_id", referencedColumnName = "id")
    private CropEntity cropEntity;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlotEntity getPlot() {
        return plot;
    }

    public void setPlot(PlotEntity plot) {
        this.plot = plot;
    }

    public SensorEntity getSensor() {
        return sensor;
    }

    public void setSensor(SensorEntity sensor) {
        this.sensor = sensor;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public IrrigationSystemEntity getIrrigationSystem() {
        return irrigationSystem;
    }

    public void setIrrigationSystem(IrrigationSystemEntity irrigationSystem) {
        this.irrigationSystem = irrigationSystem;
    }

    public CropEntity getCropEntity() {
        return cropEntity;
    }

    public void setCropEntity(CropEntity cropEntity) {
        this.cropEntity = cropEntity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
