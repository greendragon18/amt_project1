/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.UserDTO;
import ch.heig.amt.project1.entities.Organisation;
import ch.heig.amt.project1.entities.User;
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
public class UserJpaDao implements UserDaoLocal {

    @EJB
    private OrganisationDaoLocal organisationDao;
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void create(User user) {
        em.persist(user);
        em.flush();
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }
    
    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    @Override
    public List<User> findAllUserOfAnOrganisation(Long idOrganisation){
        return em.createNamedQuery("findAllUserOfAnOrganisation").setParameter("idOrganisation", idOrganisation).getResultList();
    }
    
    @Override
    public UserDTO entityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        
        userDTO.setIdUser(user.getIdUser());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        
        return userDTO;
    }
    
    @Override
    public User dtoToNewEntity(UserDTO userDTO, Long idOrganisation) throws Exception{
        Organisation organisation = organisationDao.findById(idOrganisation);
        if(organisation == null ||userDTO.getFirstName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) 
            throw new Exception("Missing information");
        
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());
        user.setOrganisation(organisation);
        
        return user;
    }
    
    @Override
    public User dtoToEntity(UserDTO userDTO, Long idOrganisation) throws Exception{
        Organisation organisation = organisationDao.findById(idOrganisation);
        User user = em.find(User.class, userDTO.getIdUser());
        if(organisation == null ||user == null || userDTO.getFirstName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) 
            throw new Exception("Missing information");
        
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());
        user.setOrganisation(organisation);
        
        return user;
    }
    
}
