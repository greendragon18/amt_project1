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
import javax.ejb.Local;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author braodck
 */
@Local
public interface FactDaoLocal {

    public void create(Fact fact);

    public void update(Fact fact);

    public void delete(Fact fact);

    public Fact findById(Long id);

    public Double findObservationAvridgeBySensorAndDate(Long idSensor, Date date);

    public FactDTO entityToDTO(Fact fact);

    public Fact findDailyStatByIdSensorAndDate(Long idSensor, Date date);

    public List<Fact> findDailyStatByDate(Date date);

    public Fact findCounterByIdSensor(Long idSensor);

    public List<Fact> findAllCounter();

    public List<Fact> findAll();

    public Fact findCounterByIdSensorForUpdate(Long idSensor);

    public Fact findDailyStatByIdSensorAndDateForUpdate(Long idSensor, Date date);
    
}
