package com.irrigation.system.api;

import com.irrigation.system.model.CropDTO;
import com.irrigation.system.model.PlotDTO;
import com.irrigation.system.model.SensorDTO;
import com.irrigation.system.model.StatusDTO;
import com.irrigation.system.service.CropService;
import com.irrigation.system.service.PlotService;

import com.irrigation.system.service.SensorService;
import com.irrigation.system.service.StatusService;
import com.irrigation.system.util.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IrrigationSystemAPI {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlotService plotService;

    @Autowired
    private CropService cropService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private StatusService statusService;

    //PLOT CRUD METHODS
    @PostMapping("/plot/create")
    public ResponseEntity<Object> createPlot(@RequestBody PlotDTO plotDTO){
        try {
            PlotDTO retPlotDTO = plotService.createPlot(plotDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retPlotDTO);
        }
        catch(Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/plots")
    public ResponseEntity<Object> getAllPlots(){
        return ResponseEntity.status(HttpStatus.OK).body(plotService.getAllPlots());
    }

    @PutMapping("/plot/edit")
    public ResponseEntity<Object> editPlot(@RequestBody PlotDTO plotDTO){
        try {
            PlotDTO retPlotDTO = plotService.editPlot(plotDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retPlotDTO);
        }
        catch(Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/plot/delete/{plotId}")
    public ResponseEntity<Object> editPlot(@PathVariable("plotId") Integer plotId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(plotService.deletePlot(plotId));
        }
        catch(Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //CROP CRUD METHODS
    @PostMapping("/crop/create")
    public ResponseEntity<Object> createCrop(@RequestBody CropDTO cropDTO){
        try{
            CropDTO retCropDTO = cropService.createCrop(cropDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retCropDTO);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/crop/edit")
    public ResponseEntity<Object> editCrop(@RequestBody CropDTO cropDTO){
        try{
            CropDTO retCropDTO = cropService.editCrop(cropDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retCropDTO);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/crops")
    public ResponseEntity<Object> getAllCrops(){
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getAllCrops());
    }

    @DeleteMapping("/crop/delete/{cropId}")
    public ResponseEntity<Object> createCrop(@PathVariable("cropId") Integer cropId){
        try{
            String retString = cropService.deleteCrop(cropId);
            return ResponseEntity.status(HttpStatus.OK).body(retString);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Sensor CRUD
    @PostMapping("/sensor/create")
    public ResponseEntity<Object> createSensor(@RequestBody SensorDTO sensorDTO){
        try{
            SensorDTO retSensorDTO = sensorService.createSensor(sensorDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retSensorDTO);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Status CRUD
    @PostMapping("/status/create")
    public ResponseEntity<Object> createStatus(@RequestBody StatusDTO statusDTO){
        try{
            StatusDTO retStatusDTO = statusService.createStatus(statusDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retStatusDTO);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/status/getByDesc")
    public ResponseEntity<Object> getStatusByDescription(@RequestBody StatusDTO statusDTO){
        try{
            StatusDTO retStatusDTO = statusService.getStatusByDescription(statusDTO.getDescription());
            return ResponseEntity.status(HttpStatus.OK).body(retStatusDTO);
        }
        catch (Exception e){
            logger.error(UtilityClass.converExceptionToString(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
