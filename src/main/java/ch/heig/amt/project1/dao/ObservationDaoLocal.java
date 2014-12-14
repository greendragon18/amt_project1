/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.ObservationDTO;
import ch.heig.amt.project1.entities.Observation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 *@author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Local
public interface ObservationDaoLocal {

    public void create(Observation observation);

    public void update(Observation observation);

    public void delete(Observation observation);

    public Observation findById(Long id);
    
    public List<Observation> findLast1000Observation(Long idSensor);
    
    public ObservationDTO entityToDTO(Observation observation);
    
    public Observation dtoToEntity(ObservationDTO observationDTO) throws Exception;
    
    public Observation dtoToNewEntity(ObservationDTO observationDTO, Long idSensor) throws Exception;
    
}
