/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.DayStatsDTO;
import ch.heig.amt.project1.entities.DayStats;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Stateless
public class DayStatsJpaDao implements DayStatsDaoLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(DayStats dayStats) {
        em.persist(dayStats);
        em.flush();
    }

    @Override
    public void update(DayStats dayStats) {
        em.merge(dayStats);
    }

    @Override
    public void delete(DayStats dayStats) {
        em.remove(dayStats);
    }

    @Override
    public DayStats findById(Long id) {
        return em.find(DayStats.class, id);
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
    public DayStats findByIdSensorAndDate(Long idSensor, Date date){
        try{
            return (DayStats) em.createNamedQuery("findDayStatsByIdSensorAndDate").setParameter("idSensor", idSensor).setParameter("date", date).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    @Override
    public List<DayStats> findByDate(Date date){
        return em.createNamedQuery("findDayStatsByDate").setParameter("date", date).getResultList();
    }
    
    @Override
    public DayStatsDTO entityToDTO(DayStats dayStats){
        DayStatsDTO dayStatsDTO = new DayStatsDTO();
        
        dayStatsDTO.setMin(dayStats.getMin());
        dayStatsDTO.setMax(dayStats.getMax());
        dayStatsDTO.setAverage(dayStats.getAverage());
        dayStatsDTO.setIdSensor(dayStats.getIdSensor());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        dayStatsDTO.setDate(df.format(dayStats.getDate()));
        dayStatsDTO.setIsPublic(dayStats.getIsPublic());
        dayStatsDTO.setFkOrganisation(dayStats.getOrganisation().getIdOrganisation());
        return dayStatsDTO;
    }
}
