package com.irrigation.system.service.impl;

import com.irrigation.system.entity.StatusEntity;
import com.irrigation.system.model.StatusDTO;
import com.irrigation.system.repository.StatusRepository;
import com.irrigation.system.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public StatusDTO createStatus(StatusDTO statusDTO) throws Exception {
        StatusEntity statusEntity = statusRepository.getStatusEntityByDesc(statusDTO.getDescription());
        if(statusEntity != null){
            throw new Exception("Status already exists!");
        }
        else{
            statusEntity = new StatusEntity();
            statusEntity.setDescription(statusDTO.getDescription());
            StatusEntity retStatusEntity = statusRepository.save(statusEntity);
            StatusDTO retStatusDTO = new StatusDTO();
            retStatusDTO.setId(retStatusEntity.getId());
            retStatusDTO.setDescription(retStatusEntity.getDescription());
            return retStatusDTO;
        }
    }

    @Override
    public StatusDTO getStatusByDescription(String description) throws Exception {
        StatusEntity statusEntity = statusRepository.getStatusEntityByDesc(description);
        if(statusEntity == null)
        {
            throw new Exception("Status Not Found!");
        }
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(statusEntity.getId());
        statusDTO.setDescription(statusEntity.getDescription());
        return statusDTO;
    }
}
