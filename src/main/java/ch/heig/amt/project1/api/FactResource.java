/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import ch.heig.amt.project1.dao.DayStatsDaoLocal;
import ch.heig.amt.project1.dao.ObservationsCountDaoLocal;
import ch.heig.amt.project1.dto.DayStatsDTO;
import ch.heig.amt.project1.dto.ObservationsCountDTO;
import ch.heig.amt.project1.entities.DayStats;
import ch.heig.amt.project1.entities.ObservationsCount;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author bradock
 */
@Path("facts")
@Stateless
public class FactResource {
    
    @EJB
    ObservationsCountDaoLocal observationsCountDao;
    @EJB
    DayStatsDaoLocal dayStatsDao;
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces("application/json")
    public List<ObservationsCountDTO> getFacts() {
        List<ObservationsCount> observationsCounts = observationsCountDao.findAll();
        List<ObservationsCountDTO> observationsCountsDTO = new LinkedList<>();
        
        for(ObservationsCount observationsCount : observationsCounts){
            observationsCountsDTO.add(observationsCountDao.entityToDTO(observationsCount));
        }
        return observationsCountsDTO;
    }
    
    @GET @Path("dayStats")
    @Produces("application/json")
    public List<DayStatsDTO> getDayStats(@QueryParam("date") String day) {
        try {
            System.out.println("Day : "+day);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = df.parse(day);
            
            List<DayStats> dayStatsList = dayStatsDao.findByDate(date);
            List<DayStatsDTO> dayStatsDTOList = new LinkedList<>();
            
            for(DayStats dayStats : dayStatsList){
                dayStatsDTOList.add(dayStatsDao.entityToDTO(dayStats));
            }
            return dayStatsDTOList;
        } catch (ParseException ex) {
            Logger.getLogger(FactResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //DateFormat df = new SimpleDateFormat("yy-MM-d");
    
}
