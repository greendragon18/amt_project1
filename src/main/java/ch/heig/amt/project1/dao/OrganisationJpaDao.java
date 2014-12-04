/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.OrganisationDTO;
import ch.heig.amt.project1.entities.Organisation;
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
public class OrganisationJpaDao implements OrganisationDaoLocal {
    
    @EJB
    private UserDaoLocal userDao;

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Organisation organisation) {
        em.persist(organisation);
        em.flush();
    }

    @Override
    public void update(Organisation organisation) {
        em.merge(organisation);
    }

    @Override
    public void delete(Organisation organisation) {
        em.remove(organisation);
    }
    
    @Override
    public Organisation findById(Long id) {
        return em.find(Organisation.class, id);
    }
    
    @Override
    public List<Organisation> findAll(){
        return em.createNamedQuery("findAllOrganisation").getResultList();
    }
    
    @Override
    public OrganisationDTO entityToDTO(Organisation organisation){
        OrganisationDTO organisationDTO = new OrganisationDTO();
        
        organisationDTO.setIdOrganisation(organisation.getIdOrganisation());
        organisationDTO.setName(organisation.getName());
        organisationDTO.setFkManager(organisation.getManager().getIdUser());
        
        return organisationDTO;
    }
    
    @Override
    public Organisation dtoToEntity(OrganisationDTO organisationDTO) throws Exception{
        if(organisationDTO.getIdOrganisation() == null || organisationDTO.getName() == null || organisationDTO.getFkManager() == null) 
            throw new Exception("missing information");
        
        Organisation organisation = em.find(Organisation.class, organisationDTO.getIdOrganisation());
        
        organisation.setName(organisationDTO.getName());
        organisation.setManager(userDao.findById(organisationDTO.getFkManager()));
         
        return organisation;
    }
    
    @Override
    public void dtoToEntity(Organisation organisation, OrganisationDTO organisationDTO){
        organisation.setName(organisationDTO.getName());
    }
}
