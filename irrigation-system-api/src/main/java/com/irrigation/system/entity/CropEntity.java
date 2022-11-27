package com.irrigation.system.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Crop")
public class CropEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(name = "crop_size")
    private Double cropSize;

    @Column(name = "supply_required")
    private Double supplyRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id")
    private PlotEntity plot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSupplyRequired() {
        return supplyRequired;
    }

    public void setSupplyRequired(Double supplyRequired) {
        this.supplyRequired = supplyRequired;
    }

    public PlotEntity getPlot() {
        return plot;
    }

    public void setPlot(PlotEntity plot) {
        this.plot = plot;
    }

    public Double getCropSize() {
        return cropSize;
    }

    public void setCropSize(Double cropSize) {
        this.cropSize = cropSize;
    }

}
