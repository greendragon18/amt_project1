/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.ObservationDTO;
import ch.heig.amt.project1.entities.Observation;
import ch.heig.amt.project1.entities.Sensor;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *@author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Stateless
public class ObservationJpaDao implements ObservationDaoLocal {
    
    @EJB
    SensorDaoLocal sensorDao;
    @EJB 
    ObservationsCountDaoLocal observationsCountDao;

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Observation observation) {
        em.persist(observation);
        em.flush();
    }

    @Override
    public void update(Observation observation) {
        em.merge(observation);
    }

    @Override
    public void delete(Observation observation) {
        em.remove(observation);
    }
    
    @Override
    public Observation findById(Long id) {
        return em.find(Observation.class, id);
    }
    
    @Override
    public List<Observation> findLast1000Observation(Long idSensor){
        return em.createNamedQuery("findLast1000Observation").setParameter("idSensor", idSensor).setMaxResults(10000).getResultList();
    }
    
    @Override
    public ObservationDTO entityToDTO(Observation observation){
        ObservationDTO observationDTO = new ObservationDTO();
        
        observationDTO.setIdObservation(observation.getIdObservation());
        observationDTO.setValue(observation.getValue());
        observationDTO.setTimestamp(observation.getTimestamp().getTime());
        
        return observationDTO;
    }
    
    @Override
    public Observation dtoToEntity(ObservationDTO observationDTO) throws Exception{
        Observation observation = em.find(Observation.class, observationDTO.getIdObservation());
        
        if(observation == null) throw new Exception("Observation find not find");
        if(observationDTO.getValue() == null || observationDTO.getTimestamp() == null) throw new Exception("Missing information");
        
        observation.setValue(observationDTO.getValue());
        observation.setTimestamp(new Date(observationDTO.getTimestamp()));
        
        return observation;
    }

    @Override
    public Observation dtoToNewEntity(ObservationDTO observationDTO, Long idSensor) throws Exception{
        Sensor sensor = sensorDao.findById(idSensor);
        
        if(sensor == null) throw new Exception("Sensor find not find");
        if(observationDTO.getValue() == null || observationDTO.getTimestamp() == null) throw new Exception("Missing information");
        
        Observation observation = new Observation();
        observation.setValue(observationDTO.getValue());
        observation.setTimestamp(new Date(observationDTO.getTimestamp()));
        observation.setSensor(sensor);
        
        return observation;
    }
}
