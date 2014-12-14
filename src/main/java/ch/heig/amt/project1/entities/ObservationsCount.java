/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findObservationsCountByIdSensor",
            query = "SELECT o FROM ObservationsCount o WHERE o.idSensor = :idSensor"
    ),
    @NamedQuery(
            name = "findAllObservationsCount",
            query = "SELECT o FROM ObservationsCount o"
    )
})
@PrimaryKeyJoinColumn(name =  "ID_OBSERVATION_COUNT", referencedColumnName = "IDFACT")
public class ObservationsCount extends Fact implements Serializable{
    @Column(unique=true,  nullable = false)
    private Long idSensor;
    @Column(nullable = false) 
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
