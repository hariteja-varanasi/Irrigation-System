package com.irrigation.system.service.impl;

import com.irrigation.system.entity.IrrigationSystemEntity;
import com.irrigation.system.model.IrrigationSystemDTO;
import com.irrigation.system.repository.IrrigationSystemRepository;
import com.irrigation.system.service.IrrigationSystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrrigationSystemServiceImpl implements IrrigationSystemService {

    @Autowired
    private IrrigationSystemRepository irrigationSystemRepository;

    @Override
    public IrrigationSystemDTO createIrrigationSystem(IrrigationSystemDTO irrigationSystemDTO) throws Exception {
        IrrigationSystemEntity irrigationSystemEntity = new IrrigationSystemEntity();
        BeanUtils.copyProperties(irrigationSystemDTO, irrigationSystemEntity);
        IrrigationSystemEntity retIrrigationSystemEntity = irrigationSystemRepository.save(irrigationSystemEntity);
        IrrigationSystemDTO retIrrigationSystemDTO = new IrrigationSystemDTO();
        BeanUtils.copyProperties(retIrrigationSystemEntity, retIrrigationSystemDTO);
        return retIrrigationSystemDTO;
    }
}
