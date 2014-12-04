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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alin Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findLast1000Observation",
            query = "SELECT o FROM Observation o"
//            query = "SELECT o FROM Observation o JOIN o.sensor s WHERE s.idSensor=:id "
    )
})
public class Observation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idObservation;
    @Column(name="OBSERVATION_VALUE")
    private Integer value;
    @Column(name="OBSERVATION_TIMESTEMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestemp;
    
    @ManyToOne
    private Sensor sensor;
    
    public Observation(){}

    public Long getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(Long idObservation) {
        this.idObservation = idObservation;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(Date timestemp) {
        this.timestemp = timestemp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    
}
