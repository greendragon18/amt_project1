/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.SensorDTO;
import ch.heig.amt.project1.entities.Sensor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Local
public interface SensorDaoLocal {

    public void create(Sensor sensor);

    public void update(Sensor sensor);

    public void delete(Sensor sensor);

    public Sensor findById(Long id);

    public SensorDTO entityToDTO(Sensor sensor);

    public Sensor dtoToEntity(SensorDTO sensorDTO) throws Exception;
    
    public List<Sensor> findAll();

    public Sensor dtoToNewEntity(SensorDTO sensorDTO, Long idOrganisation) throws Exception;

    public List<Sensor> findByOrganisation(Long idOrganisation);
    
}
