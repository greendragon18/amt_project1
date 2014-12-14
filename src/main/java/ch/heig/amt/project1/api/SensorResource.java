/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import ch.heig.amt.project1.business.ObservationsFlowDaysStatsProssessorLocal;
import ch.heig.amt.project1.business.ObservationsFlowCountProcessorLocal;
import ch.heig.amt.project1.dao.ObservationDaoLocal;
import ch.heig.amt.project1.dao.SensorDaoLocal;
import ch.heig.amt.project1.dto.ObservationDTO;
import ch.heig.amt.project1.dto.SensorDTO;
import ch.heig.amt.project1.entities.Observation;
import ch.heig.amt.project1.entities.Sensor;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Path("sensors")
@Stateless
public class SensorResource {
    @EJB
    private ObservationsFlowCountProcessorLocal ObservationsFlowCountProcessor;
    @EJB
    private ObservationsFlowDaysStatsProssessorLocal ObservationsFlowDaysStatsProssessor;
    @EJB
    SensorDaoLocal sensorDao;
    @EJB
    ObservationDaoLocal observationDao;
    
    
    @Context
    private UriInfo context;

    public SensorResource() {
    }

    @GET
    @Produces("application/json")
    public List<SensorDTO> getSensors() {
        List<Sensor> sensors = sensorDao.findAll();
        LinkedList<SensorDTO> sensorsDTO = new LinkedList<>();
        
        for(Sensor sensor : sensors){
            sensorsDTO.add(sensorDao.entityToDTO(sensor));
        }
        
        return sensorsDTO;
    }
    
    @GET @Path("{id}")
    @Produces("application/json")
    public SensorDTO getSensor(@PathParam("id") Long id, @Context HttpServletResponse response) throws IOException {
        Sensor sensor = sensorDao.findById(id);
        if (sensor != null)
            return sensorDao.entityToDTO(sensor);
        else
            response.sendError(404);
        return null;
    }
    
    @GET @Path("{id}/observations")
    @Produces("application/json")
    public List<ObservationDTO> getObservations(@PathParam("id") Long id) {
        List<Observation> observations = observationDao.findLast1000Observation(id);
        List<ObservationDTO> observationsDTO = new LinkedList<>();
        
        for(Observation observation : observations){
            observationsDTO.add(observationDao.entityToDTO(observation));
        }
        return observationsDTO;
    }
    
    @POST @Path("{id}/observations")
    @Consumes("application/json")
    @Produces("application/json")
    public ObservationDTO createObservations(ObservationDTO observationDTO, @PathParam("id") Long id, @Context HttpServletResponse response) throws IOException {   
        try {
            Observation observation = observationDao.dtoToNewEntity(observationDTO, id);
            observationDao.create(observation);
            ObservationsFlowCountProcessor.processObservation(observation);
            ObservationsFlowDaysStatsProssessor.processObservation(observation);
            return observationDao.entityToDTO(observation);
        } catch (Exception ex) {
            Logger.getLogger(SensorResource.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404);
        }
        return null;
    }
}
