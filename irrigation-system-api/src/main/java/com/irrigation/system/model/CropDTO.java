package com.irrigation.system.model;

public class CropDTO {

    private Integer id;
    private String name;
    private Double supplyRequired;
    private Integer plotId;
    private Double cropSize;
    private long minimumHoursNeeded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSupplyRequired() {
        return supplyRequired;
    }

    public void setSupplyRequired(Double supplyRequired) {
        this.supplyRequired = supplyRequired;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public Double getCropSize() {
        return cropSize;
    }

    public void setCropSize(Double cropSize) {
        this.cropSize = cropSize;
    }

    public long getMinimumHoursNeeded() {
        return minimumHoursNeeded;
    }

    public void setMinimumHoursNeeded(long minimumHoursNeeded) {
        this.minimumHoursNeeded = minimumHoursNeeded;
    }
}
