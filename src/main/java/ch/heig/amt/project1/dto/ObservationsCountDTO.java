/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import java.io.Serializable;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
public class ObservationsCountDTO extends FactDTO implements Serializable{
    private Long idSensor;
    private Long nbObervation;

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Long getNbObervation() {
        return nbObervation;
    }

    public void setNbObervation(Long nbObervation) {
        this.nbObervation = nbObervation;
    }
    
}
