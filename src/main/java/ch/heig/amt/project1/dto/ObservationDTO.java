/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author bradock
 */
public class ObservationDTO implements Serializable {
    private Long idObservation;
    private Integer value;
    private Date timestamp;

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

    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
