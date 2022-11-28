package com.irrigation.system.service.impl;

import com.irrigation.system.entity.CropEntity;
import com.irrigation.system.entity.PlotEntity;
import com.irrigation.system.entity.SensorEntity;
import com.irrigation.system.entity.StatusEntity;
import com.irrigation.system.model.CropDTO;
import com.irrigation.system.model.PlotDTO;
import com.irrigation.system.model.SensorDTO;
import com.irrigation.system.repository.CropRepository;
import com.irrigation.system.repository.PlotRepository;
import com.irrigation.system.repository.SensorRepository;
import com.irrigation.system.repository.StatusRepository;
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

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public PlotDTO createPlot(PlotDTO plotDTO) throws Exception {
        PlotEntity plotEntity = new PlotEntity();
        //Adding crops
        if (plotDTO.getCropDTOS() != null && !plotDTO.getCropDTOS().isEmpty()) {
            List<CropEntity> cropEntities = new ArrayList<CropEntity>();
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
            plotEntity.setCrops(cropEntities);
        }
        //Adding sensors
        List<SensorDTO> sensorDTOS = plotDTO.getSensors();
        if (sensorDTOS != null && !sensorDTOS.isEmpty()) {
            List<SensorEntity> sensorEntities = new ArrayList<SensorEntity>();
            for (SensorDTO sensorDTO : sensorDTOS) {
                SensorEntity sensorEntity = new SensorEntity();
                StatusEntity statusEntity = statusRepository.getStatusEntityByDesc(sensorDTO.getStatus());
                if (statusEntity == null) {
                    throw new Exception("Invalid status");
                }
                sensorEntity.setPlot(plotEntity);
                sensorEntity.setStatusEntity(statusEntity);
                sensorEntities.add(sensorEntity);
            }
            plotEntity.setSensors(sensorEntities);
        }
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
        List<SensorEntity> retSensorEntities = retPlotEntity.getSensors();
        List<SensorDTO> retSensorDTOS = new ArrayList<>();
        retSensorEntities.stream().forEach(sensorEntity -> {
            SensorDTO retSensorDTO = new SensorDTO();
            BeanUtils.copyProperties(sensorEntity, retSensorDTO);
            retSensorDTO.setPlotId(sensorEntity.getPlot().getId());
            retSensorDTO.setStatus(sensorEntity.getStatusEntity().getDescription());
            retSensorDTOS.add(retSensorDTO);
        });
        if (retPlotEntity.getId() > 0) {
            plotDTO.setId(retPlotEntity.getId());
            plotDTO.setCropDTOS(retCropDTOS);
            plotDTO.setSensors(retSensorDTOS);
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
            //Listing all the crops
            List<CropEntity> cropEntities = plotEntity.getCrops();
            List<CropDTO> cropDTOS = new ArrayList<CropDTO>();
            cropEntities.forEach(cropEntity -> {
                CropDTO cropDTO = new CropDTO();
                BeanUtils.copyProperties(cropEntity, cropDTO);
                cropDTO.setPlotId(cropEntity.getPlot().getId());
                cropDTOS.add(cropDTO);
            });
            plotDTO.setCropDTOS(cropDTOS);
            //Listing all the sensors
            List<SensorEntity> sensorEntities = plotEntity.getSensors();
            List<SensorDTO> sensorDTOS = new ArrayList<SensorDTO>();
            sensorEntities.forEach(sensorEntity -> {
                SensorDTO sensorDTO = new SensorDTO();
                BeanUtils.copyProperties(sensorEntity, sensorDTO);
                sensorDTO.setPlotId(sensorEntity.getPlot().getId());
                sensorDTO.setStatus(sensorEntity.getStatusEntity().getDescription());
                sensorDTOS.add(sensorDTO);
            });
            plotDTO.setSensors(sensorDTOS);
            plotDTOS.add(plotDTO);
        });
        return plotDTOS;
    }

    @Override
    public PlotDTO editPlot(PlotDTO plotDTO) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(plotDTO.getId());
        if (optionalPlotEntity.isEmpty()) {
            throw new Exception("Plot does not exist!");
        }
        PlotEntity plotEntity = optionalPlotEntity.get();
        plotEntity.setAvailableSize(plotDTO.getAvailableSize());
        plotEntity.setTotalSize(plotDTO.getTotalSize());
        //Editing crops for the plot
        List<CropDTO> cropDTOS = plotDTO.getCropDTOS();
        if (cropDTOS != null && !cropDTOS.isEmpty()) {
            List<CropEntity> cropEntities = plotEntity.getCrops();
            for (CropDTO cropDTO : cropDTOS) {
                CropEntity cropEntity = cropRepository.getById(cropDTO.getId());
                if (cropEntity == null) {
                    throw new Exception("Crop does not exist!");
                }
                if (cropEntity != null) {
                    if (cropDTO.getCropSize() > plotEntity.getAvailableSize()) {
                        throw new Exception("Plot does not have enough space!");
                    } else {
                        BeanUtils.copyProperties(cropDTO, cropEntity);
                        plotEntity.setAvailableSize(plotEntity.getAvailableSize() - cropDTO.getCropSize());
                    }

                } else {
                    if (cropDTO.getCropSize() > plotEntity.getAvailableSize()) {
                        throw new Exception("Plot does not have enough space!");
                    } else {
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
        //Editing sensors for the plot
        List<SensorDTO> sensorDTOS = plotDTO.getSensors();
        if (sensorDTOS != null && !sensorDTOS.isEmpty()) {
            List<SensorEntity> sensorEntities = plotEntity.getSensors();
            for (SensorDTO sensorDTO : sensorDTOS) {
                SensorEntity sensorEntity = sensorRepository.getById(sensorDTO.getId());
                if (sensorEntity != null) {
                    StatusEntity newStatusEntity = statusRepository.getStatusEntityByDesc(sensorDTO.getStatus());
                    if (newStatusEntity == null) {
                        throw new Exception("Invalid status");
                    }
                    sensorEntity.setStatusEntity(newStatusEntity);
                } else {
                    sensorEntity = new SensorEntity();
                    StatusEntity statusEntity = statusRepository.getStatusEntityByDesc(sensorDTO.getStatus());
                    if (statusEntity == null) {
                        throw new Exception("Invalid status");
                    }
                    sensorEntity.setPlot(plotEntity);
                    sensorEntity.setStatusEntity(statusEntity);
                    sensorEntities.add(sensorEntity);
                }
            }
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
        List<SensorEntity> retSensorEntities = retPlotEntity.getSensors();
        List<SensorDTO> retSensorDTOS = new ArrayList<>();
        retSensorEntities.stream().forEach(sensorEntity -> {
            SensorDTO retSensorDTO = new SensorDTO();
            BeanUtils.copyProperties(sensorEntity, retSensorDTO);
            retSensorDTO.setPlotId(sensorEntity.getPlot().getId());
            retSensorDTO.setStatus(sensorEntity.getStatusEntity().getDescription());
            retSensorDTOS.add(retSensorDTO);
        });
        retPlotDTO.setSensors(retSensorDTOS);
        return retPlotDTO;
    }

    @Override
    public String deletePlot(Integer plotId) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(plotId);
        if (optionalPlotEntity.isEmpty()) {
            throw new Exception("Plot does not exist!");
        } else {
            plotRepository.deleteById(plotId);
            return "Plot deleted successfully!";
        }
    }
}
