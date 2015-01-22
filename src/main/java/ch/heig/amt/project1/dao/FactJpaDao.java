/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.FactDTO;
import ch.heig.amt.project1.entities.DailyStatFact;
import ch.heig.amt.project1.entities.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author braodck
 */
@Stateless
public class FactJpaDao implements FactDaoLocal {

    private static final Logger LOG = Logger.getLogger(FactJpaDao.class.getName());
    
    @PersistenceContext
    EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void create(Fact fact) {
        try{
            em.persist(fact);
            em.flush();
        }catch(Exception e){
            LOG.info("Cannot create fact : " + e.getMessage());
        }
    }

    @Override
    public void update(Fact fact){
        em.merge(fact); 
    }

    @Override
    public void delete(Fact fact) {
        em.remove(fact);
    }

    @Override
    public Fact findById(Long id) {
        return em.find(DailyStatFact.class, id);
    }
    
    @Override
    public List<Fact> findAll(){
        return em.createNamedQuery("findAllFact").setMaxResults(1000).getResultList();
    }

    @Override
    public Double findObservationAvridgeBySensorAndDate(Long idSensor, Date date){
        
        long time = date.getTime();
        //at midnight
        date.setTime(time - time % (24 * 60 * 60 * 1000));
        //just befor next Day
        Date nextDay = new Date(date.getTime() + (24 * 60 * 60 * 1000) -1);
        
        return (Double) em.createNamedQuery("findObservationAvridgeBySensorAndDate").setParameter("idSensor", idSensor).setParameter("startDate", date).setParameter("endDate", nextDay).getSingleResult();
    }

    @Override
    public Fact findDailyStatByIdSensorAndDate(Long idSensor, Date date){
        try{
            return (Fact) em.createNamedQuery("findDailyStatByIdSensorAndDate").setParameter("idSensor", idSensor).setParameter("date", date).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    @Override
    public Fact findDailyStatByIdSensorAndDateForUpdate(Long idSensor, Date date){
        try{
            return (Fact) em.createNamedQuery("findDailyStatByIdSensorAndDateForUpdate").setParameter("idSensor", idSensor).setParameter("date", date).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Fact> findDailyStatByDate(Date date){
        return em.createNamedQuery("findDailyStatByDate").setParameter("date", date).getResultList();
    }

    @Override
    public Fact findCounterByIdSensor(Long idSensor){
        try{
            return (Fact) em.createNamedQuery("findCounterByIdSensor").setParameter("idSensor", idSensor).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    @Override
    public Fact findCounterByIdSensorForUpdate(Long idSensor){
        try{
            return (Fact) em.createNamedQuery("findCounterByIdSensorForUpdate").setParameter("idSensor", idSensor).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Fact> findAllCounter(){
        return em.createNamedQuery("findAllCounter").getResultList();
    }
    
    @Override
    public FactDTO entityToDTO(Fact fact){
        FactDTO factDTO = new FactDTO();
        
        factDTO.setIdFact(fact.getIdFact());
        factDTO.setType(fact.getType());
        factDTO.setIsPublic(fact.getIsPublic());
        factDTO.setIdOrganisation(fact.getOrganisation().getIdOrganisation());
        factDTO.setProperties(fact.getProperties());
        return factDTO;
    }

}