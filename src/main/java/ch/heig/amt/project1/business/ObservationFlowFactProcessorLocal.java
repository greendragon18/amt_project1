/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.business;

import ch.heig.amt.project1.entities.Observation;
import javax.ejb.Local;

/**
 *
 * @author braodck
 */
@Local
public interface ObservationFlowFactProcessorLocal {
    public void processCounterFact(Observation observation);
    public void processDailyStatFact(Observation observation);
    
}
