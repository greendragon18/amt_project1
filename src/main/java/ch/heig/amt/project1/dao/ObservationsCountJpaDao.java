/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.ObservationsCountDTO;
import ch.heig.amt.project1.entities.ObservationsCount;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bradock
 */
@Stateless
public class ObservationsCountJpaDao implements ObservationsCountDaoLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(ObservationsCount observationsCount) {
        em.persist(observationsCount);
        em.flush();
    }

    @Override
    public void update(ObservationsCount observationsCount) {
        em.merge(observationsCount);
    }

    @Override
    public void delete(ObservationsCount observationsCount) {
        em.remove(observationsCount);
    }
    
    @Override
    public ObservationsCount findById(Long id) {
        return em.find(ObservationsCount.class, id);
    }
    
    @Override
    public ObservationsCount findByIdSensor(Long idSensor){
        try{
            return (ObservationsCount) em.createNamedQuery("findObservationsCountByIdSensor").setParameter("idSensor", idSensor).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    @Override
    public List<ObservationsCount> findAll(){
        return em.createNamedQuery("findAllObservationsCount").getResultList();
    }
    
    @Override
    public ObservationsCountDTO entityToDTO (ObservationsCount observationsCount){
        ObservationsCountDTO observationsCountDTO = new ObservationsCountDTO();
        
        observationsCountDTO.setNbObervation(observationsCount.getNbObervation());
        observationsCountDTO.setIdSensor(observationsCount.getIdSensor());
        observationsCountDTO.setIsPublic(observationsCount.getIsPublic());
        observationsCountDTO.setFkOrganisation(observationsCount.getOrganisation().getIdOrganisation());
        
        return observationsCountDTO;
    }
}
