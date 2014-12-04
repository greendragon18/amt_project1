/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.ObservationDTO;
import ch.heig.amt.project1.entities.Observation;
import ch.heig.amt.project1.entities.Sensor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alin Curty
 */
@Stateless
public class ObservationJpaDao implements ObservationDaoLocal {
    
    @EJB
    SensorDaoLocal sensorDao;

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
//        return em.createNamedQuery("findLast1000Observation").setParameter("id", idSensor).setMaxResults(10000).getResultList();
        return em.createNamedQuery("findLast1000Observation").setMaxResults(10000).getResultList();
    }
    
    @Override
    public ObservationDTO entityToDTO(Observation observation){
        ObservationDTO observationDTO = new ObservationDTO();
        
        observationDTO.setIdObservation(observation.getIdObservation());
        observationDTO.setValue(observation.getValue());
        observationDTO.setTimestemp(observationDTO.getTimestemp());
        
        return observationDTO;
    }
    
    @Override
    public Observation dtoToEntity(ObservationDTO observationDTO){
        Observation observation = em.find(Observation.class, observationDTO.getIdObservation());
        
        observation.setValue(observationDTO.getValue());
        observation.setTimestemp(observationDTO.getTimestemp());
        
        return observation;
    }

    @Override
    public void dtoToEntity(Observation observation, ObservationDTO observationDTO, Long idSensor) throws Exception{
        Sensor sensor = sensorDao.findById(idSensor);
        
        if(sensor == null) throw new Exception("no sensor find");
        
        observation.setValue(observationDTO.getValue());
        observation.setTimestemp(observationDTO.getTimestemp());
        observation.setSensor(sensor);
    }
}
