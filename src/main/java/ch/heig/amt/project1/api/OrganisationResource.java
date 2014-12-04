/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import ch.heig.amt.project1.dao.OrganisationDaoLocal;
import ch.heig.amt.project1.dao.SensorDaoLocal;
import ch.heig.amt.project1.dao.UserDaoLocal;
import ch.heig.amt.project1.dto.OrganisationDTO;
import ch.heig.amt.project1.dto.SensorDTO;
import ch.heig.amt.project1.dto.UserDTO;
import ch.heig.amt.project1.entities.Organisation;
import ch.heig.amt.project1.entities.Sensor;
import ch.heig.amt.project1.entities.User;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author bradock
 */
@Path("organisations")
@Stateless
public class OrganisationResource {
    @Context
    private UriInfo context;
    
    @EJB
    private OrganisationDaoLocal organisationDao;
    @EJB
    private SensorDaoLocal sensorDao;
    @EJB
    private UserDaoLocal userDao;
    
    @GET
    @Produces("application/json")
    public List<OrganisationDTO> getOrganistaions() {
        List<Organisation> organisations = organisationDao.findAll();
        LinkedList<OrganisationDTO> organisationsDTO = new LinkedList<>();
        
        for(Organisation organisation : organisations){
            organisationsDTO.add(organisationDao.entityToDTO(organisation));
        }   
        return organisationsDTO;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public OrganisationDTO createOrganistaion(OrganisationDTO organisationDTO) {
        Organisation organisation = new Organisation();
        
        organisationDao.dtoToEntity(organisation, organisationDTO); 
        organisationDao.create(organisation);
          
        return organisationDao.entityToDTO(organisation);
    }
    
    @GET @Path("{idOrganisation}")
    @Produces("application/json")
    public OrganisationDTO getOrganistaion(@PathParam("idOrganisation") Long id) {
        Organisation organisation = organisationDao.findById(id);  
        return organisationDao.entityToDTO(organisation);
    }
    
    @PUT @Path("{idOrganisation}")
    @Consumes("application/json")
    @Produces("application/json")
    public OrganisationDTO updateOrganistaion(@PathParam("idOrganisation") Long id, OrganisationDTO organisationDTO) throws Exception {
        
        Organisation organisation = organisationDao.dtoToEntity(organisationDTO); 
        organisationDao.update(organisation);
          
        return organisationDao.entityToDTO(organisation);
    }
    
    @DELETE @Path("{idOrganisation}")
    public void deleteOrganistaion(@PathParam("idOrganisation") Long id, @Context HttpServletResponse response) throws Exception { 
        Organisation organisation = organisationDao.findById(id);
        if(organisation == null) response.sendError(404);
        
        organisationDao.delete(organisation);
    }
    
    @GET @Path("{idOrganisation}/sensors")
    @Produces("application/json")
    public List<SensorDTO> getSensors() {
        List<Sensor> sensors = sensorDao.findAll();
        LinkedList<SensorDTO> sensorsDTO = new LinkedList<>();
        
        for(Sensor sensor : sensors){
            sensorsDTO.add(sensorDao.entityToDTO(sensor));
        }   
        return sensorsDTO;
    }
    
    @POST @Path("{idOrganisation}/sensors")
    @Consumes("application/json")
    @Produces("application/json")
    public SensorDTO createSensor(SensorDTO sensorDTO, @PathParam("idOrganisation") Long id, @Context HttpServletResponse response) throws IOException{
        try {
            Sensor sensor = sensorDao.dtoToNewEntity(sensorDTO, id);
            sensorDao.create(sensor);
            return sensorDao.entityToDTO(sensor);
        } catch (Exception ex) {
            Logger.getLogger(OrganisationResource.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404);
        }
        return null;
    }
    
    @GET @Path("{idOrganisation}/sensors/{idSensor}")
    @Produces("application/json")
    public SensorDTO getSensor(@PathParam("idSensor") Long id, @Context HttpServletResponse response) throws IOException{ 
        Sensor sensor = sensorDao.findById(id);
        
        if(sensor == null) response.sendError(404);
        
        return sensorDao.entityToDTO(sensor);
    }
    
    @PUT @Path("{idOrganisation}/sensors/{idSensor}")
    @Consumes("application/json")
    public void updateSensor(SensorDTO sensorDTO, @PathParam("idSensor") Long id, @Context HttpServletResponse response) throws IOException{ 
        Sensor sensor;
        try {
            sensor = sensorDao.dtoToEntity(sensorDTO);
            sensorDao.update(sensor);
        } catch (Exception ex) {
            Logger.getLogger(OrganisationResource.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404);
        }
    }
    
    @DELETE @Path("{idOrganisation}/sensors/{idSensor}")
    public void deleteSensor(@PathParam("idSensor") Long id, @Context HttpServletResponse response) throws IOException{ 
        Sensor sensor = sensorDao.findById(id);
        if(sensor == null) response.sendError(404);
        
        sensorDao.delete(sensor);
    }
    
    @GET @Path("{idOrganisation}/users")
    @Produces("application/json")
    public List<UserDTO> getUsers(@PathParam("idOrganisation") Long id) {
        List<User> users = userDao.findAllUserOfAnOrganisation(id);
        LinkedList<UserDTO> usersDTO = new LinkedList<>();
        
        for(User user : users){
            usersDTO.add(userDao.entityToDTO(user));
        }   
        return usersDTO;
    }
    
    @POST @Path("{idOrganisation}/users")
    @Consumes("application/json")
    @Produces("application/json")
    public UserDTO createUser(UserDTO userDTO, @PathParam("idOrganisation") Long id, @Context HttpServletResponse response) throws IOException{
        try {
            User user = userDao.dtoToNewEntity(userDTO, id);
            userDao.create(user);
            return userDao.entityToDTO(user);
        } catch (Exception ex) {
            Logger.getLogger(OrganisationResource.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404);
        }
        return null;
    }
    
    @GET @Path("{idOrganisation}/users/{idUser}")
    @Produces("application/json")
    public UserDTO getUser(@PathParam("idUser") Long id, @Context HttpServletResponse response) throws IOException{ 
        User user = userDao.findById(id);
        
        if(user == null) response.sendError(404);
        
        return userDao.entityToDTO(user);
    }
    
    @PUT @Path("{idOrganisation}/users/{idUser}")
    @Consumes("application/json")
    public void updateUser(UserDTO userDTO, @PathParam("idOrganisation") Long id, @Context HttpServletResponse response) throws IOException{ 
        User user;
        try {
            user = userDao.dtoToEntity(userDTO, id);
            userDao.update(user);
        } catch (Exception ex) {
            Logger.getLogger(OrganisationResource.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(404);
        }
    }
    
    @DELETE @Path("{idOrganisation}/users/{idUser}")
    public void deleteUser(@PathParam("idUser") Long id, @Context HttpServletResponse response) throws IOException{ 
        User user = userDao.findById(id);
        if(user == null) response.sendError(404);
        
        userDao.delete(user);
    }
}
