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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findLast1000Observation",
            query = "SELECT o FROM Observation o WHERE o.sensor.idSensor = :idSensor"
    )
})
public class Observation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idObservation;
    @Column(name="OBSERVATION_VALUE", nullable = false)
    private Double value;
    
    @Column(name="OBSERVATION_TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Sensor sensor;
    
    public Observation(){}

    public Long getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(Long idObservation) {
        this.idObservation = idObservation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestemp) {
        this.timestamp = timestemp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    
}
