package com.irrigation.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IrrigationSystemHistoryDTO {

    private Integer id;
    @JsonProperty(value = "plot_id")
    private Integer plotId;
    @JsonProperty(value = "sensor_id")
    private Integer sensorId;
    private String status;
    @JsonProperty(value = "irrigation_system_id")
    private Integer irrigationSystemId;
    @JsonProperty(value = "crop_id")
    private Integer cropId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIrrigationSystemId() {
        return irrigationSystemId;
    }

    public void setIrrigationSystemId(Integer irrigationSystemId) {
        this.irrigationSystemId = irrigationSystemId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

}
