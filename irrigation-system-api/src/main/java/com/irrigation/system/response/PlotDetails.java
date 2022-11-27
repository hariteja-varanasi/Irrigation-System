package com.irrigation.system.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.irrigation.system.model.CropDTO;
import com.irrigation.system.model.PlotDTO;

import java.util.List;

public class PlotDetails {

    @JsonProperty(value = "plot_details")
    private PlotDTO plotDTO;

    @JsonProperty(value = "crop_details")
    private List<CropDTO> cropDTOS;

    public PlotDTO getPlotDTO() {
        return plotDTO;
    }

    public void setPlotDTO(PlotDTO plotDTO) {
        this.plotDTO = plotDTO;
    }

    public List<CropDTO> getCropDTOS() {
        return cropDTOS;
    }

    public void setCropDTOS(List<CropDTO> cropDTOS) {
        this.cropDTOS = cropDTOS;
    }

}
