package com.irrigation.system.service;

import com.irrigation.system.model.SensorDTO;
import com.irrigation.system.model.StatusDTO;

public interface SensorService {

    public SensorDTO createSensor(SensorDTO sensorDTO) throws Exception;

    public SensorDTO updateSensorStatus(SensorDTO sensorDTO, String description) throws Exception;
}
