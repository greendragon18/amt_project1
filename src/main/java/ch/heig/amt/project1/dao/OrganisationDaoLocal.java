/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.OrganisationDTO;
import ch.heig.amt.project1.entities.Organisation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Local
public interface OrganisationDaoLocal {

    public void create(Organisation organisation);

    public void update(Organisation organisation);

    public void delete(Organisation organisation);

    public Organisation findById(Long id);

    public List<Organisation> findAll();

    public OrganisationDTO entityToDTO(Organisation organisation);
    
    public Organisation dtoToEntity(OrganisationDTO organisationDTO) throws Exception;
    
    public Organisation dtoToNewEntity(OrganisationDTO organisationDTO) throws Exception;

}
