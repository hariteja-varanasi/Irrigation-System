package com.irrigation.system.service.impl;

import com.irrigation.system.entity.PlotEntity;
import com.irrigation.system.entity.SensorEntity;
import com.irrigation.system.entity.StatusEntity;
import com.irrigation.system.model.SensorDTO;
import com.irrigation.system.model.StatusDTO;
import com.irrigation.system.repository.PlotRepository;
import com.irrigation.system.repository.SensorRepository;
import com.irrigation.system.repository.StatusRepository;
import com.irrigation.system.service.SensorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public SensorDTO createSensor(SensorDTO sensorDTO) throws Exception {
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(sensorDTO.getPlotId());
        if(optionalPlotEntity.isEmpty()){
            throw new Exception("Plot does not exist!");
        }
        StatusEntity statusEntity = statusRepository.getStatusEntityByDesc("IDLE");
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setPlot(optionalPlotEntity.get());
        sensorEntity.setStatusEntity(statusEntity);
        SensorEntity retSensorEntity = sensorRepository.save(sensorEntity);
        SensorDTO retSensorDTO = new SensorDTO();
        retSensorDTO.setId(retSensorEntity.getId());
        retSensorDTO.setPlotId(retSensorEntity.getPlot().getId());
        retSensorDTO.setStatus(statusEntity.getDescription());
        return retSensorDTO;
    }


}
