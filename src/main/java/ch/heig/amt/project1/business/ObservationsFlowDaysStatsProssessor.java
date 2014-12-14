/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.business;

import ch.heig.amt.project1.dao.DayStatsDaoLocal;
import ch.heig.amt.project1.entities.DayStats;
import ch.heig.amt.project1.entities.Observation;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ObservationsFlowDaysStatsProssessor implements ObservationsFlowDaysStatsProssessorLocal {
    
    @EJB
    DayStatsDaoLocal dayStatsDao;

    @Override
    public void processObservation(Observation observation){
        
        DayStats dayStats = dayStatsDao.findByIdSensorAndDate(observation.getSensor().getIdSensor(), observation.getTimestamp());
        
        if(dayStats == null){
            dayStats = new DayStats();
            dayStats.setMin(observation.getValue());
            dayStats.setMax(observation.getValue());
            dayStats.setTotal(observation.getValue());
            dayStats.setNbObservation(1l);
            dayStats.setAverage(observation.getValue());
            dayStats.setDate(observation.getTimestamp());
            dayStats.setIdSensor(observation.getSensor().getIdSensor());
            dayStats.setIsPublic(observation.getSensor().getIsPublic());
            dayStats.setOrganisation(observation.getSensor().getOrganisation());
            dayStatsDao.create(dayStats);
        }else{
            if(dayStats.getMin() > observation.getValue())
                dayStats.setMin(observation.getValue());
            else if(dayStats.getMax() < observation.getValue())
                dayStats.setMax(observation.getValue());
            dayStats.setTotal(dayStats.getTotal() + observation.getValue());
            dayStats.setNbObservation(dayStats.getNbObservation() + 1);
            dayStats.setAverage(dayStats.getTotal()/dayStats.getNbObservation());
        }
    }
}
