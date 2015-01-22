/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.business;

import ch.heig.amt.project1.dao.FactDaoLocal;
import ch.heig.amt.project1.entities.CounterFact;
import ch.heig.amt.project1.entities.DailyStatFact;
import ch.heig.amt.project1.entities.Observation;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import static jdk.nashorn.internal.codegen.Compiler.LOG;

/**
 *
 * @author braodck
 */
@Stateless
public class ObservationFlowFactProcessor implements ObservationFlowFactProcessorLocal {

    @EJB
    private FactDaoLocal factDao;
  

    @Override
    public void processCounterFact(Observation observation) {
        createCounterFactIfNotExists(observation);

        CounterFact counterFact = (CounterFact) factDao.findCounterByIdSensorForUpdate(observation.getSensor().getIdSensor());
        counterFact.setNbObervation(counterFact.getNbObervation() + 1);
                
        factDao.update(counterFact);
            
    }

    @Override
    public void processDailyStatFact(Observation observation) {
        createDailyFactIfNotExists(observation);

        DailyStatFact dailyStatFact = (DailyStatFact) factDao.findDailyStatByIdSensorAndDateForUpdate(observation.getSensor().getIdSensor(), observation.getTimestamp());


        if (dailyStatFact.getMin() > observation.getValue()) {
            dailyStatFact.setMin(observation.getValue());
        } else if (dailyStatFact.getMax() < observation.getValue()) {
            dailyStatFact.setMax(observation.getValue());
        }
        dailyStatFact.setTotal(dailyStatFact.getTotal() + observation.getValue());
        dailyStatFact.setNbObservation(dailyStatFact.getNbObservation() + 1);
        dailyStatFact.setAverage(dailyStatFact.getTotal() / dailyStatFact.getNbObservation());

        factDao.update(dailyStatFact);
               
    }

    private void createDailyFactIfNotExists(Observation observation) {
        DailyStatFact dailyStatFact = (DailyStatFact) factDao.findDailyStatByIdSensorAndDate(observation.getSensor().getIdSensor(), observation.getTimestamp());

        if (dailyStatFact == null) {
            dailyStatFact = new DailyStatFact();
            dailyStatFact.setMin(observation.getValue());
            dailyStatFact.setMax(observation.getValue());
            dailyStatFact.setTotal(0d);
            dailyStatFact.setNbObservation(0l);
            dailyStatFact.setAverage(0d);
            dailyStatFact.setDate(observation.getTimestamp());
            dailyStatFact.setIdSensor(observation.getSensor().getIdSensor());
            dailyStatFact.setIsPublic(observation.getSensor().getIsPublic());
            dailyStatFact.setOrganisation(observation.getSensor().getOrganisation());

            try {
                factDao.create(dailyStatFact);
            } catch (Exception e) {
                LOG.info("*** Exception while creating a fact, probably a DUPLICATE key (concurrent issue)");
            }
        }
    }
    
    private void createCounterFactIfNotExists(Observation observation) {
        CounterFact counterFact = (CounterFact) factDao.findCounterByIdSensor(observation.getSensor().getIdSensor());
        if (counterFact == null) {
            System.out.println("CREATE COUNTER");
            counterFact = new CounterFact();
            counterFact.setIdSensor(observation.getSensor().getIdSensor());
            counterFact.setNbObervation(0l);
            counterFact.setOrganisation(observation.getSensor().getOrganisation());
            counterFact.setIsPublic(observation.getSensor().getIsPublic());

            try {
                factDao.create(counterFact);
            } catch (Exception e) {
                LOG.info("*** Exception while creating a fact, probably a DUPLICATE key (concurrent issue)");
            }
        }
    }
}