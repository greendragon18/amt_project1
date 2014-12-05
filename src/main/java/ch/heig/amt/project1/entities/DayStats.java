/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bradock
 */
@Entity
@PrimaryKeyJoinColumn(name =  "ID_DAY_STATS", referencedColumnName = "IDFACT")
public class DayStats extends Fact implements Serializable{
    
    @Column(name="DAY_STATS_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(nullable = false)
    private Long min;
    @Column(nullable = false)
    private Long max;
    @Column(nullable = false)
    private Long average;
    @Column(unique=true,  nullable = false)
    private Long idSensor;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getAverage() {
        return average;
    }

    public void setAverage(Long average) {
        this.average = average;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

}
