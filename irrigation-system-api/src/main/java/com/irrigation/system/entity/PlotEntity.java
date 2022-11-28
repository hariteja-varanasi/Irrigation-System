package com.irrigation.system.entity;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Plot")
public class PlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "total_size")
    private Double totalSize;

    @Column(name = "available_size")
    private Double availableSize;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    private List<CropEntity> crops;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    private List<SensorEntity> sensors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Double totalSize) {
        this.totalSize = totalSize;
    }

    public List<CropEntity> getCrops() {
        return crops;
    }

    public void setCrops(List<CropEntity> crops) {
        this.crops = crops;
    }

    public Double getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(Double availableSize) {
        this.availableSize = availableSize;
    }

    public List<SensorEntity> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorEntity> sensors) {
        this.sensors = sensors;
    }

}
