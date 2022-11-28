package com.irrigation.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorDTO {

    private Integer id;
    @JsonProperty(value = "plot_id")
    private Integer plotId;
    @JsonProperty(value = "status")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
