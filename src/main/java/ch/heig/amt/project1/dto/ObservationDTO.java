/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import java.io.Serializable;

/**
 *
 * @author bradock
 */
public class ObservationDTO implements Serializable {
    private Long idObservation;
    private Double value;
    private Long timestamp;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestemp) {
        this.timestamp = timestemp;
    }
    
}
