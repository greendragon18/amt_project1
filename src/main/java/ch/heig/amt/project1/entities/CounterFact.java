/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
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
            name = "findCounterByIdSensor",
            query = "SELECT c FROM CounterFact c WHERE c.idSensor = :idSensor"
    ),
    @NamedQuery(
            name = "findCounterByIdSensorForUpdate",
            query = "SELECT c FROM CounterFact c WHERE c.idSensor = :idSensor",
            lockMode = PESSIMISTIC_WRITE
    ),
    @NamedQuery(
            name = "findAllCounter",
            query = "SELECT c FROM CounterFact c"
    )
})
@PrimaryKeyJoinColumn(name =  "ID_OBSERVATION_COUNT", referencedColumnName = "IDFACT")
public class CounterFact extends Fact implements Serializable{
    @Column(unique=true,  nullable = false)
    private Long idSensor;
    @Column(nullable = false) 
    private Long nbObervation;
    
    public CounterFact(){
        super();
        super.setType("counter");
    }
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("nbObervation", nbObervation);
        properties.put("idSensor", idSensor);
        return properties;
    }

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
