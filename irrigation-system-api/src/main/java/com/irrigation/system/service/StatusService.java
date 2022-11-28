package com.irrigation.system.service;

import com.irrigation.system.model.StatusDTO;

public interface StatusService {

    public StatusDTO createStatus(StatusDTO statusDTO) throws Exception;

    public StatusDTO getStatusByDescription(String description) throws Exception;

}
