package ch.heig.amt.project1.business;

import ch.heig.amt.project1.dao.ObservationsCountDaoLocal;
import ch.heig.amt.project1.entities.Observation;
import ch.heig.amt.project1.entities.ObservationsCount;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author bradock
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ObservationsFlowCountProcessor implements ObservationsFlowCountProcessorLocal {

    @EJB
    ObservationsCountDaoLocal observationsCountDao;

    @Override
    public void processObservation(Observation observation) {
        ObservationsCount observationsCount = observationsCountDao.findByIdSensor(observation.getSensor().getIdSensor());

        //first observation for this sensor
        if (observationsCount == null) {
            observationsCount = new ObservationsCount();
            observationsCount.setIdSensor(observation.getSensor().getIdSensor());
            observationsCount.setNbObervation(1l);
            observationsCount.setOrganisation(observation.getSensor().getOrganisation());
            observationsCount.setIsPublic(observation.getSensor().getIsPublic());
            observationsCountDao.create(observationsCount);
        } else {
            observationsCount.setNbObervation(observationsCount.getNbObervation() + 1);
        }
    }

}
