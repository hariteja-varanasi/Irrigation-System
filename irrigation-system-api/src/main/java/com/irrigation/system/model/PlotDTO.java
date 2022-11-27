package com.irrigation.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.irrigation.system.entity.CropEntity;
import java.util.List;

public class PlotDTO {

    private Integer id;
    private Double totalSize;
    private Double availableSize;
    @JsonProperty(value = "crops")
    private List<CropDTO> cropDTOS;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Double totalSize) {
        this.totalSize = totalSize;
    }

    public Double getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(Double availableSize) {
        this.availableSize = availableSize;
    }

    public List<CropDTO> getCropDTOS() {
        return cropDTOS;
    }

    public void setCropDTOS(List<CropDTO> cropDTOS) {
        this.cropDTOS = cropDTOS;
    }

}
