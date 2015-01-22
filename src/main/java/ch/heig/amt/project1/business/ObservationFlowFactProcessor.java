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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    @EJB
    private ObservationFlowFactProcessorLocal selfViaContainer;

    @Override
    public void processCounterFact(Observation observation) {
        selfViaContainer.createCounterFactIfNotExists(observation);

        int nbTry = 100;

        while (nbTry > 0) {
            try {
                CounterFact counterFact = (CounterFact) factDao.findCounterByIdSensor(observation.getSensor().getIdSensor());
                //WHEN I TRY TO GET COUNTER FACT THEY ARE ALLWAYS NULL AND I DON'T KNOW WHY
                
                if(counterFact == null){
                    System.out.println("COUNTER FACT IS NULL " + observation.getSensor().getIdSensor());
                    throw new Exception();
                }else{
                    System.out.println("COUTNER FACT IS NOT NULL");
                }
                counterFact.setNbObervation(counterFact.getNbObervation() + 1);

                
                factDao.update(counterFact);
                break;
            } catch (EJBException e) {
                LOG.info("*** Exception while updating a fact, probably a concurrent issue");
                nbTry--;
                Random rand = new Random();
                try {
                    //random sleep between 50 and 150
                    Thread.sleep(rand.nextInt(101) + 50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ObservationFlowFactProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch (Exception e){
                nbTry--;
                System.out.println("FACT WAS NULL");
            }
        }
    }

    @Override
    public void processDailyStatFact(Observation observation) {
        selfViaContainer.createDailyFactIfNotExists(observation);

        int nbTry = 100;

        while (nbTry > 0) {
            try {
                DailyStatFact dailyStatFact = (DailyStatFact) factDao.findDailyStatByIdSensorAndDate(observation.getSensor().getIdSensor(), observation.getTimestamp());

                if(dailyStatFact == null){
                    System.out.println("DAILY STAT FACT IS NULL");
                }else{
                    System.out.println("DAILY STAT FACT IS NOT NULL");
                }
                if (dailyStatFact.getMin() > observation.getValue()) {
                    dailyStatFact.setMin(observation.getValue());
                } else if (dailyStatFact.getMax() < observation.getValue()) {
                    dailyStatFact.setMax(observation.getValue());
                }
                dailyStatFact.setTotal(dailyStatFact.getTotal() + observation.getValue());
                dailyStatFact.setNbObservation(dailyStatFact.getNbObservation() + 1);
                dailyStatFact.setAverage(dailyStatFact.getTotal() / dailyStatFact.getNbObservation());

                factDao.update(dailyStatFact);
                break;
            } catch (Exception e) {
                LOG.info("*** Exception while updating a fact, probably a concurrent issue");
                nbTry--;
                Random rand = new Random();
                try {
                    //random sleep between 50 and 150
                    Thread.sleep(rand.nextInt(101) + 50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ObservationFlowFactProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Override
    public void createDailyFactIfNotExists(Observation observation) {
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

    @Override
    public void createCounterFactIfNotExists(Observation observation) {
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
