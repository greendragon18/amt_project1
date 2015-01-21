/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.init;

import ch.heig.amt.project1.dao.OrganisationDaoLocal;
import ch.heig.amt.project1.dao.SensorDaoLocal;
import ch.heig.amt.project1.dao.UserDaoLocal;
import ch.heig.amt.project1.entities.Organisation;
import ch.heig.amt.project1.entities.Sensor;
import ch.heig.amt.project1.entities.User;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Singleton
@Startup
public class InitData {
    
    @EJB
    private OrganisationDaoLocal organisationDao;
    @EJB
    private UserDaoLocal userDao;
    @EJB
    private SensorDaoLocal sensorDao;
    
    @PostConstruct
    private void startup(){
//        Organisation organisation1 = new Organisation();
//        organisation1.setName("HEIG");
//        organisationDao.create(organisation1);
//        
//        Organisation organisation2 = new Organisation();
//        organisation2.setName("AMT");
//        organisationDao.create(organisation2);
//        
//        User user1 = new User();
//        user1.setFirstName("A");
//        user1.setLastName("AA");
//        user1.setEmail("a@a.com");
//        user1.setPassword("1234");
//        user1.setOrganisation(organisation1);
//        
//        User user2 = new User();
//        user2.setFirstName("B");
//        user2.setLastName("BB");
//        user2.setEmail("b@b.com");
//        user2.setPassword("1234");
//        user2.setOrganisation(organisation1);
//        
//        User user3 = new User();
//        user3.setFirstName("C");
//        user3.setLastName("CC");
//        user3.setEmail("c@c.com");
//        user3.setPassword("1234");
//        user3.setOrganisation(organisation2);
//        
//        User user4 = new User();
//        user4.setFirstName("D");
//        user4.setLastName("DD");
//        user4.setEmail("d@d.com");
//        user4.setPassword("1234");
//        user4.setOrganisation(organisation2);
//        
//        userDao.create(user1);
//        userDao.create(user2);
//        userDao.create(user3);
//        userDao.create(user4);
//        
//        organisation1.setManager(user1);
//        organisation2.setManager(user3);
//        
//        organisationDao.update(organisation1);
//        organisationDao.update(organisation2);
//        
//        Sensor sensor1 = new Sensor();
//        sensor1.setName("temperature sensor");
//        sensor1.setDescription("ROOM_1");
//        sensor1.setType("TEMPERATURE");
//        sensor1.setIsPublic(true);
//        sensor1.setOrganisation(organisation1);
//        
//        Sensor sensor2 = new Sensor();
//        sensor2.setName("temperature sensor");
//        sensor2.setDescription("ROOM_2");
//        sensor2.setType("TEMPERATURE");
//        sensor2.setIsPublic(false);
//        sensor2.setOrganisation(organisation1);
//        
//        Sensor sensor3 = new Sensor();
//        sensor3.setName("temperature sensor");
//        sensor3.setDescription("CPU_1");
//        sensor3.setType("TEMPERATURE");
//        sensor3.setIsPublic(true);
//        sensor3.setOrganisation(organisation2);
//        
//        Sensor sensor4 = new Sensor();
//        sensor4.setName("temperature sensor");
//        sensor4.setDescription("CPU_2");
//        sensor4.setType("TEMPERATURE");
//        sensor4.setIsPublic(false);
//        sensor4.setOrganisation(organisation2);
//        
//        sensorDao.create(sensor1);
//        sensorDao.create(sensor2);
//        sensorDao.create(sensor3);
//        sensorDao.create(sensor4);
     
    }
}
