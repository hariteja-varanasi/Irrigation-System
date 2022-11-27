package com.irrigation.system.service;

import com.irrigation.system.model.PlotDTO;
import com.irrigation.system.response.PlotDetails;

import java.util.List;

public interface PlotService {

    public PlotDTO createPlot(PlotDTO plotDTO) throws Exception;

    public List<PlotDTO> getAllPlots();

    public PlotDTO editPlot(PlotDTO plotDTO) throws Exception;

    public String deletePlot(Integer plotId) throws Exception;

}
