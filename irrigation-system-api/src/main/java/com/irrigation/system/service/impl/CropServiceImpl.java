package com.irrigation.system.service.impl;

import com.irrigation.system.entity.CropEntity;
import com.irrigation.system.entity.PlotEntity;
import com.irrigation.system.model.CropDTO;
import com.irrigation.system.repository.CropRepository;
import com.irrigation.system.repository.PlotRepository;
import com.irrigation.system.service.CropService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private PlotRepository plotRepository;

    @Override
    public CropDTO createCrop(CropDTO cropDTO) throws Exception {
        CropEntity cropEntity = new CropEntity();
        cropEntity.setName(cropDTO.getName());
        cropEntity.setCropSize(cropDTO.getCropSize());
        cropEntity.setSupplyRequired(cropDTO.getSupplyRequired());
        if(cropDTO.getPlotId() == null){
            throw new Exception("Plot ID can not be null!");
        }
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(cropDTO.getPlotId());
        if (optionalPlotEntity.isEmpty()){
            throw new Exception("Plot does not exist!");
        }
        else{
            PlotEntity plotEntity = optionalPlotEntity.get();
            if(cropDTO.getCropSize() > plotEntity.getAvailableSize()){
                throw new Exception("Plot does not have enough space!");
            }
            else
            {
                plotEntity.setAvailableSize(plotEntity.getAvailableSize() - cropDTO.getCropSize());
                plotRepository.save(plotEntity);
                cropEntity.setPlot(plotEntity);
                CropEntity retCropEntity = cropRepository.save(cropEntity);
                if(retCropEntity.getId() > 0){
                    cropDTO.setId(cropEntity.getId());
                    return cropDTO;
                }
            }
        }
        return null;
    }

    @Override
    public List<CropDTO> getAllCrops() {
        List<CropEntity> cropEntities = cropRepository.findAll();
        List<CropDTO> cropDTOS = new ArrayList<CropDTO>();
        cropEntities.stream().forEach(cropEntity -> {
            CropDTO cropDTO = new CropDTO();
            BeanUtils.copyProperties(cropEntity, cropDTO);
            cropDTO.setPlotId(cropEntity.getPlot().getId());
            cropDTOS.add(cropDTO);
        });
        return cropDTOS;
    }

    @Override
    public CropDTO editCrop(CropDTO cropDTO) throws Exception {
        Optional<CropEntity> optionalCropEntity = cropRepository.findById(cropDTO.getId());
        if(optionalCropEntity.isEmpty()){
            throw new Exception("Crop does not exist!");
        }
        Optional<PlotEntity> optionalPlotEntity = plotRepository.findById(cropDTO.getPlotId());
        if(optionalPlotEntity.isEmpty()){
            throw new Exception("Plot does not exist!");
        }
        CropEntity cropEntity = optionalCropEntity.get();
        PlotEntity plotEntity = optionalPlotEntity.get();
        if(cropDTO.getCropSize() > plotEntity.getAvailableSize()){
            throw new Exception("Plot does not have enough space!");
        }
        if(cropDTO.getCropSize() > cropEntity.getCropSize()){
            plotEntity.setAvailableSize(plotEntity.getAvailableSize() - (cropDTO.getCropSize() - cropEntity.getCropSize()));
        }
        else if(cropDTO.getCropSize() < cropEntity.getCropSize()){
            plotEntity.setAvailableSize(plotEntity.getAvailableSize() - (cropEntity.getCropSize() - cropDTO.getCropSize()));
        }
        BeanUtils.copyProperties(cropDTO, cropEntity);
        cropEntity.setPlot(plotEntity);
        CropEntity retCropEntity =cropRepository.save(cropEntity);
        plotRepository.save(plotEntity);
        CropDTO retCropDTO = new CropDTO();
        BeanUtils.copyProperties(retCropEntity, retCropDTO);
        retCropDTO.setPlotId(retCropEntity.getPlot().getId());
        return retCropDTO;
    }

    @Override
    public String deleteCrop(Integer cropId) throws Exception {
        Optional<CropEntity> optionalCropEntity = cropRepository.findById(cropId);
        if(optionalCropEntity.isEmpty()){
            throw new Exception("Crop does not exist!");
        }
        cropRepository.deleteById(cropId);
        return "Crop deleted successfully!";
    }
}
