/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import ch.heig.amt.project1.dao.FactDaoLocal;
import ch.heig.amt.project1.dto.FactDTO;
import ch.heig.amt.project1.entities.Fact;
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
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Path("facts")
@Stateless
public class FactResource {
    
    @EJB
    FactDaoLocal factDao;
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces("application/json")
    public List<FactDTO> getFacts(@QueryParam("type") String type, @QueryParam("date") String day) {
        if (type == null) type = "default";
        
        switch(type){
            case "counter":
                List<Fact> counterFacts = factDao.findAllCounter();
                List<FactDTO> counterFactsDTO = new LinkedList<>();
                
                for(Fact observationsCount : counterFacts){
                    counterFactsDTO.add(factDao.entityToDTO(observationsCount));
                }
                return counterFactsDTO;
            case "dailyStat":
                try {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = df.parse(day);

                    List<Fact> dailyStatFacts = factDao.findDailyStatByDate(date);
                    List<FactDTO> dailyStatFactsDTO = new LinkedList<>();

                    for(Fact dailyStatFact : dailyStatFacts){
                        dailyStatFactsDTO.add(factDao.entityToDTO(dailyStatFact));
                    }
                    return dailyStatFactsDTO;
                } catch (ParseException ex) {
                    Logger.getLogger(FactResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            default:
                List<Fact> facts = factDao.findAll();
                List<FactDTO> factsDTO = new LinkedList<>();
                
                for(Fact fact : facts){
                    factsDTO.add(factDao.entityToDTO(fact));
                }
                return factsDTO;  
        }

    }
    
}
