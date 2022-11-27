package com.irrigation.system.service;

import com.irrigation.system.model.CropDTO;

import java.util.List;

public interface CropService {

    public CropDTO createCrop(CropDTO cropDTO) throws Exception;

    public List<CropDTO> getAllCrops();

    public CropDTO editCrop(CropDTO cropDTO) throws Exception;

    public String deleteCrop(Integer cropId) throws Exception;

}
