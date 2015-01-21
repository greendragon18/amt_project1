/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.FactDTO;
import ch.heig.amt.project1.entities.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Local;

/**
 *
 * @author braodck
 */
@Local
public interface FactDaoLocal {

    public void create(Fact fact);

    public void update(Fact fact) throws EJBException;

    public void delete(Fact fact);

    public Fact findById(Long id);

    public Double findObservationAvridgeBySensorAndDate(Long idSensor, Date date);

    public FactDTO entityToDTO(Fact fact);

    public Fact findDailyStatByIdSensorAndDate(Long idSensor, Date date);

    public List<Fact> findDailyStatByDate(Date date);

    public Fact findCounterByIdSensor(Long idSensor);

    public List<Fact> findAllCounter();

    public List<Fact> findAll();
    
}
