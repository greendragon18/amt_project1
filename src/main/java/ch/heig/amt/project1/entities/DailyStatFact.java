/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findDailyStatByIdSensorAndDate",
            query = "SELECT d FROM DailyStatFact d WHERE d.idSensor = :idSensor AND d.date = :date"
    ),
    @NamedQuery(
            name = "findDailyStatByIdSensorAndDateForUpdate",
            query = "SELECT d FROM DailyStatFact d WHERE d.idSensor = :idSensor AND d.date = :date",
            lockMode = PESSIMISTIC_WRITE
    ),
    @NamedQuery(
            name = "findDailyStatByDate",
            query = "SELECT d FROM DailyStatFact d WHERE d.date = :date"
    ),
    @NamedQuery(
            name = "findObservationAvridgeBySensorAndDate",
            query = "SELECT AVG(o.value) from Observation o WHERE o.sensor.idSensor = :idSensor AND o.timestamp BETWEEN :startDate AND :endDate"
    )
})
@PrimaryKeyJoinColumn(name =  "ID_DAY_STATS", referencedColumnName = "IDFACT")
public class DailyStatFact extends Fact implements Serializable{
    
    @Column(name="DAY_STATS_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name="DAY_STATS_MIN", nullable = false)
    private Double min;
    @Column(name="DAY_STATS_MAX", nullable = false)
    private Double max;
    @Column(nullable = false)
    private Double average;
    @Column(nullable = false)
    private Double total;
    @Column(nullable = false)
    private Long nbObservation;
    @Column(nullable = false)
    private Long idSensor;
    @Column(unique=true, nullable = false)
    private Long singlePersistCheck;
    
    public DailyStatFact(){
        super();
        super.setType("dailyStat");
    }
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("date", date);
        properties.put("min", min);
        properties.put("max", max);
        properties.put("average", average);
        properties.put("idSensor", idSensor);
        return properties;
    }
    
    @PrePersist
    protected void onCreate() {
        singlePersistCheck = (date.getTime() - date.getTime() % (24*60*60*1000) + idSensor);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getNbObservation() {
        return nbObservation;
    }

    public void setNbObservation(Long nbObservation) {
        this.nbObservation = nbObservation;
    }

    public Long getSinglePersistCheck() {
        return singlePersistCheck;
    }

    public void setSinglePersistCheck(Long singlePersistCheck) {
        this.singlePersistCheck = singlePersistCheck;
    }
    

}
