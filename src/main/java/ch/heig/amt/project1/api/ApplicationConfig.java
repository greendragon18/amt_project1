/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Application;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        Class jsonProvider;
        try {
          jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
          resources.add(jsonProvider);
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, "*** Problem while configuring JSON!", ex);
        }

        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.heig.amt.project1.api.FactResource.class);
        resources.add(ch.heig.amt.project1.api.JacksonConfigurationProvider.class);
        resources.add(ch.heig.amt.project1.api.OrganisationResource.class);
        resources.add(ch.heig.amt.project1.api.SensorResource.class);
    }

}
