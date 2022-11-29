package com.irrigation.system.service;

import com.irrigation.system.model.IrrigationSystemHistoryDTO;

public interface IrrigationSystemHistoryService {

    public IrrigationSystemHistoryDTO irrigatePlot(IrrigationSystemHistoryDTO irrigationSystemHistoryDTO) throws Exception;

    public IrrigationSystemHistoryDTO stopPlotIrrigation(Integer id) throws Exception;
}
