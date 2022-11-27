package com.irrigation.system.service.impl;

import com.irrigation.system.entity.CropEntity;
import com.irrigation.system.entity.PlotEntity;
import com.irrigation.system.model.CropDTO;
import com.irrigation.system.model.PlotDTO;
import com.irrigation.system.repository.CropRepository;
import com.irrigation.system.repository.PlotRepository;
import com.irrigation.system.response.PlotDetails;
import com.irrigation.system.service.PlotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlotServiceImpl implements PlotService {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private CropRepository cropRepository;

    @Override
    public PlotDTO createPlot(PlotDTO plotDTO) throws Exception {
        PlotEntity plotEntity = new PlotEntity();
        List<CropEntity> cropEntities = new ArrayList<CropEntity>();
        if(plotDTO.getCropDTOS() != null && !plotDTO.getCropDTOS().isEmpty()){
            List<CropDTO> cropDTOS = plotDTO.getCropDTOS();
            for (CropDTO cropDTO : cropDTOS) {
                CropEntity cropEntity = new CropEntity();
                if (cropDTO.getCropSize() > plotDTO.getAvailableSize()) {
                    throw new Exception("Plot does not have enough space!");
                } else {
                    BeanUtils.copyProperties(cropDTO, cropEntity);
                    plotDTO.setAvailableSize(plotDTO.getAvailableSize() - cropDTO.getCropSize());
                    cropEntity.setPlot(plotEntity);
                    cropEntities.add(cropEntity);
                }
            }
        }
        plotEntity.setCrops(cropEntities);
        plotEntity.setAvailableSize(plotDTO.getAvailableSize());
        plotEntity.setTotalSize(plotDTO.getTotalSize());
        PlotEntity retPlotEntity = plotRepository.save(plotEntity);
        List<CropEntity> retCropEntities = retPlotEntity.getCrops();
        List<CropDTO> retCropDTOS = new ArrayList<CropDTO>();
        retCropEntities.stream().forEach(cropEntity -> {
            CropDTO retCropDTO = new CropDTO();
            BeanUtils.copyProperties(cropEntity, retCropDTO);
            retCropDTO.setPlotId(cropEntity.getPlot().getId());
            retCropDTOS.add(retCropDTO);
        });
        if(retPlotEntity.getId() > 0){
            plotDTO.setId(retPlotEntity.getId());
            plotDTO.setCropDTOS(retCropDTOS);
            return plotDTO;
        }
        return null;
    }

    @Override
    public List<PlotDTO> getAllPlots() {
        List<PlotEntity> plotEntities = plotRepository.findAll();
        List<PlotDTO> plotDTOS = new ArrayList<PlotDTO>();
        plotEntities.stream().forEach(plotEntity -> {
            PlotDTO plotDTO = new PlotDTO();
            BeanUtils.copyProperties(plotEntity, plotDTO);
            List<CropEntity> cropEntities = plotEntity.getCrops();
            List<CropDTO> cropDTOS = new ArrayList<CropDTO>();
            cropEntities.forEach(cropEntity -> {
                CropDTO cropDTO = new CropDTO();
                BeanUtils.copyProperties(cropEntity, cropDTO);
                cropDTO.setPlotId(cropEntity.getPlot().getId());
                cropDTOS.add(cropDTO);
            });
            plotDTO.setCropDTOS(cropDTOS);
            plotDTOS.add(plotDTO);
        });
        return plotDTOS;
    }

    @Override
    public PlotDTO editPlot(PlotDTO plotDTO) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(plotDTO.getId());
        if(optionalPlotEntity.isEmpty()){
            throw new Exception("Plot does not exist!");
        }
        PlotEntity plotEntity = optionalPlotEntity.get();
        plotEntity.setAvailableSize(plotDTO.getAvailableSize());
        plotEntity.setTotalSize(plotDTO.getTotalSize());
        List<CropDTO> cropDTOS = plotDTO.getCropDTOS();
        if(cropDTOS != null && !cropDTOS.isEmpty()){
            List<CropEntity> cropEntities = plotEntity.getCrops();
            for (CropDTO cropDTO : cropDTOS) {
                CropEntity cropEntity = cropRepository.getById(cropDTO.getId());
                if (cropEntity != null) {
                    if (cropDTO.getCropSize() > plotEntity.getAvailableSize()) {
                        throw new Exception("Plot does not have enough space!");
                    }
                    else{
                        BeanUtils.copyProperties(cropDTO, cropEntity);
                        plotEntity.setAvailableSize(plotEntity.getAvailableSize() - cropDTO.getCropSize());
                    }

                } else {
                    if (cropDTO.getCropSize() > plotEntity.getAvailableSize()) {
                        throw new Exception("Plot does not have enough space!");
                    }
                    else{
                        cropEntity = new CropEntity();
                        BeanUtils.copyProperties(cropDTO, cropEntity);
                        plotEntity.setAvailableSize(plotEntity.getAvailableSize() - cropDTO.getCropSize());
                        cropEntity.setPlot(plotEntity);
                        cropEntities.add(cropEntity);
                    }
                }
            }
            plotEntity.setCrops(cropEntities);
        }
        PlotEntity retPlotEntity = plotRepository.save(plotEntity);
        PlotDTO retPlotDTO = new PlotDTO();
        BeanUtils.copyProperties(retPlotEntity, retPlotDTO);
        List<CropEntity> retCropEntities = retPlotEntity.getCrops();
        List<CropDTO> retCropDTOS = new ArrayList<CropDTO>();
        retCropEntities.forEach(cropEntity -> {
            CropDTO cropDTO = new CropDTO();
            BeanUtils.copyProperties(cropEntity, cropDTO);
            cropDTO.setPlotId(cropEntity.getPlot().getId());
            retCropDTOS.add(cropDTO);
        });
        retPlotDTO.setCropDTOS(retCropDTOS);
        return retPlotDTO;
    }

    @Override
    public String deletePlot(Integer plotId) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(plotId);
        if(optionalPlotEntity.isEmpty()) {
            throw new Exception("Plot does not exist!");
        }
        else{
            plotRepository.deleteById(plotId);
            return "Plot deleted successfully!";
        }
    }
}
