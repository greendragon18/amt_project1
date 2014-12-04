/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.SensorDTO;
import ch.heig.amt.project1.entities.Organisation;
import ch.heig.amt.project1.entities.Sensor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alin Curty
 */
@Stateless
public class SensorJpaDao implements SensorDaoLocal {

    @EJB
    private OrganisationDaoLocal organisationDao;
    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Sensor sensor) {
        em.persist(sensor);
        em.flush();
    }

    @Override
    public void update(Sensor sensor) {
        em.merge(sensor);
    }

    @Override
    public void delete(Sensor sensor) {
        em.remove(sensor);
    }
    
    @Override
    public Sensor findById(Long id) {
        return em.find(Sensor.class, id);
    }
    
    @Override
    public List<Sensor> findAll(){
        return em.createNamedQuery("findAllSensor").getResultList();
    }
    
    @Override
    public SensorDTO entityToDTO(Sensor sensor){
        SensorDTO sensorDTO = new SensorDTO();
        
        sensorDTO.setIdSensor(sensor.getIdSensor());
        sensorDTO.setName(sensor.getName());
        sensorDTO.setDescription(sensor.getDescription());
        sensorDTO.setType(sensor.getType());
        sensorDTO.setIsPublic(sensor.getIsPublic());
        
        return sensorDTO;
    }
    
    @Override
    public Sensor dtoToEntity(SensorDTO sensorDTO) throws Exception{
        Sensor sensor = em.find(Sensor.class, sensorDTO.getIdSensor());
        
        if(sensor == null) throw new Exception("Unknown Sensor");
        
        sensor.setName(sensorDTO.getName());
        sensor.setDescription(sensorDTO.getDescription());
        sensor.setType(sensorDTO.getType());
        sensor.setIsPublic(sensorDTO.getIsPublic());
        
        return sensor;
    }
    
    @Override
    public Sensor dtoToNewEntity(SensorDTO sensorDTO, Long idOrganisation) throws Exception{
        Organisation organisation = organisationDao.findById(idOrganisation);
        
        if(organisation == null) throw new Exception("Unknown Organisation");
        
        Sensor sensor = new Sensor();
        
        sensor.setName(sensorDTO.getName());
        sensor.setDescription(sensorDTO.getDescription());
        sensor.setType(sensorDTO.getType());
        sensor.setIsPublic(sensorDTO.getIsPublic());
        sensor.setOrganisation(organisation);
        
        return sensor;
    }
    
}
