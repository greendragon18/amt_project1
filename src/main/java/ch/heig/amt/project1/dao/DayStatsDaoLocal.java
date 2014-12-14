/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.DayStatsDTO;
import ch.heig.amt.project1.entities.DayStats;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Local
public interface DayStatsDaoLocal {

    public void create(DayStats dayStats);

    public void update(DayStats dayStats);

    public void delete(DayStats dayStats);
    
    public DayStats findById(Long id);

    public DayStats findByIdSensorAndDate(Long idSensor, Date date);

    public List<DayStats> findByDate(Date date);

    public Double findObservationAvridgeBySensorAndDate(Long idSensor, Date date);
    
    public DayStatsDTO entityToDTO(DayStats dayStats);
    
}
