package com.irrigation.system.service.impl;

import com.irrigation.system.entity.*;
import com.irrigation.system.model.IrrigationSystemHistoryDTO;
import com.irrigation.system.repository.*;
import com.irrigation.system.service.IrrigationSystemHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Temporal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class IrrigationSystemHistoryServiceImpl implements IrrigationSystemHistoryService {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private IrrigationSystemRepository irrigationSystemRepository;

    @Autowired
    private IrrigationSystemHistoryRepository irrigationSystemHistoryRepository;


    @Override
    public IrrigationSystemHistoryDTO irrigatePlot(IrrigationSystemHistoryDTO irrigationSystemHistoryDTO) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(irrigationSystemHistoryDTO.getPlotId());
        if (optionalPlotEntity.isEmpty()) {
            throw new Exception("Plot does not exist!");
        }
        Optional<CropEntity> optionalCropEntity = cropRepository.findById(irrigationSystemHistoryDTO.getCropId());
        if (optionalCropEntity.isEmpty()) {
            throw new Exception("Crop does not exist!");
        }
        List<CropEntity> cropEntities = optionalPlotEntity.get().getCrops();
        if (!checkIfCropExistsInPlot(cropEntities, optionalCropEntity.get())) {
            throw new Exception("Crop does not belong to the Plot!");
        }
        Optional<SensorEntity> optionalSensorEntity = sensorRepository.findById(irrigationSystemHistoryDTO.getSensorId());
        if (optionalSensorEntity.isEmpty()) {
            throw new Exception("Sensor does not exist!");
        }
        List<SensorEntity> sensorEntities = optionalPlotEntity.get().getSensors();
        if (!checkIfSensorExistsInPlot(sensorEntities, optionalSensorEntity.get())) {
            throw new Exception("Sensor does not belong to the Plot!");
        }
        if (optionalSensorEntity.get().getStatusEntity().getDescription().equals("RUNNING")) {
            throw new Exception("Sensor already running!");
        }
        Optional<IrrigationSystemEntity> optionalIrrigationSystemEntity = irrigationSystemRepository.findById(irrigationSystemHistoryDTO.getIrrigationSystemId());
        if (optionalIrrigationSystemEntity.isEmpty()) {
            throw new Exception("Irrigation System does not exist!");
        }
        StatusEntity runningStatusEntity = statusRepository.getStatusEntityByDesc("RUNNING");
        IrrigationSystemHistoryEntity irrigationSystemHistoryEntity = new IrrigationSystemHistoryEntity();
        irrigationSystemHistoryEntity.setPlot(optionalPlotEntity.get());
        irrigationSystemHistoryEntity.setCropEntity(optionalCropEntity.get());
        irrigationSystemHistoryEntity.setSensor(optionalSensorEntity.get());
        irrigationSystemHistoryEntity.setIrrigationSystem(optionalIrrigationSystemEntity.get());
        irrigationSystemHistoryEntity.setStatus(runningStatusEntity);
        irrigationSystemHistoryEntity.setStartTime(LocalDateTime.now());
        optionalSensorEntity.get().setStatusEntity(runningStatusEntity);
        sensorRepository.save(optionalSensorEntity.get());
        IrrigationSystemHistoryEntity retIrrigationSystemHistoryEntity = irrigationSystemHistoryRepository.save(irrigationSystemHistoryEntity);
        IrrigationSystemHistoryDTO retIrrigationSystemHistoryDTO = new IrrigationSystemHistoryDTO();
        BeanUtils.copyProperties(retIrrigationSystemHistoryEntity, retIrrigationSystemHistoryDTO);
        retIrrigationSystemHistoryDTO.setIrrigationSystemId(retIrrigationSystemHistoryEntity.getIrrigationSystem().getId());
        retIrrigationSystemHistoryDTO.setPlotId(retIrrigationSystemHistoryEntity.getPlot().getId());
        retIrrigationSystemHistoryDTO.setCropId(retIrrigationSystemHistoryEntity.getCropEntity().getId());
        retIrrigationSystemHistoryDTO.setSensorId(retIrrigationSystemHistoryEntity.getSensor().getId());
        retIrrigationSystemHistoryDTO.setStatus(retIrrigationSystemHistoryEntity.getStatus().getDescription());
        retIrrigationSystemHistoryDTO.setId(retIrrigationSystemHistoryEntity.getId());
        return retIrrigationSystemHistoryDTO;
    }

    @Override
    public IrrigationSystemHistoryDTO stopPlotIrrigation(Integer id) throws Exception {
        Optional<IrrigationSystemHistoryEntity> optionalIrrigationSystemHistoryEntity = irrigationSystemHistoryRepository.findById(id);
        if (optionalIrrigationSystemHistoryEntity.isEmpty()) {
            throw new Exception("No Such Record Exists!");
        }
        if (!optionalIrrigationSystemHistoryEntity.get().getStatus().getDescription().equals("RUNNING")) {
            throw new Exception("System is not running!");
        }
        IrrigationSystemHistoryEntity irrigationSystemHistoryEntity = optionalIrrigationSystemHistoryEntity.get();
        LocalDateTime startTime = irrigationSystemHistoryEntity.getStartTime();
        LocalDateTime currentTime = LocalDateTime.now();
        if (ChronoUnit.HOURS.between(currentTime, startTime) < irrigationSystemHistoryEntity.getCropEntity().getMinimumHoursNeeded()) {
            throw new Exception("Minimum Hours of irrigation not met!");
        }
        StatusEntity completedStatusEntity = statusRepository.getStatusEntityByDesc("COMPLETED");
        StatusEntity idleStatusEntity = statusRepository.getStatusEntityByDesc("IDLE");
        irrigationSystemHistoryEntity.setStatus(completedStatusEntity);
        irrigationSystemHistoryEntity.getSensor().setStatusEntity(idleStatusEntity);
        IrrigationSystemHistoryEntity retIrrigationSystemHistoryEntity = irrigationSystemHistoryRepository.save(irrigationSystemHistoryEntity);
        IrrigationSystemHistoryDTO retIrrigationSystemHistoryDTO = new IrrigationSystemHistoryDTO();
        BeanUtils.copyProperties(retIrrigationSystemHistoryEntity, retIrrigationSystemHistoryDTO);
        retIrrigationSystemHistoryDTO.setIrrigationSystemId(retIrrigationSystemHistoryEntity.getIrrigationSystem().getId());
        retIrrigationSystemHistoryDTO.setPlotId(retIrrigationSystemHistoryEntity.getPlot().getId());
        retIrrigationSystemHistoryDTO.setCropId(retIrrigationSystemHistoryEntity.getCropEntity().getId());
        retIrrigationSystemHistoryDTO.setSensorId(retIrrigationSystemHistoryEntity.getSensor().getId());
        retIrrigationSystemHistoryDTO.setStatus(retIrrigationSystemHistoryEntity.getStatus().getDescription());
        retIrrigationSystemHistoryDTO.setId(retIrrigationSystemHistoryEntity.getId());
        return retIrrigationSystemHistoryDTO;
    }

    private boolean checkIfCropExistsInPlot(List<CropEntity> cropEntities, CropEntity cropEntity) {
        for (CropEntity loopCropEntity : cropEntities) {
            if (loopCropEntity.getId() == cropEntity.getId()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfSensorExistsInPlot(List<SensorEntity> sensorEntities, SensorEntity sensorEntity) {
        for (SensorEntity loopSensorEntity : sensorEntities) {
            if (loopSensorEntity.getId() == sensorEntity.getId()) {
                return true;
            }
        }
        return false;
    }
}
