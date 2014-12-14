/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.ObservationsCountDTO;
import ch.heig.amt.project1.entities.ObservationsCount;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Local
public interface ObservationsCountDaoLocal {

    public void create(ObservationsCount observationsCount);

    public void update(ObservationsCount observationsCount);

    public void delete(ObservationsCount observationsCount);

    public ObservationsCount findById(Long id);

    public ObservationsCount findByIdSensor(Long idSensor);

    public ObservationsCountDTO entityToDTO(ObservationsCount observationsCount);

    public List<ObservationsCount> findAll();
    
}
