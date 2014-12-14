/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import java.io.Serializable;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
public class OrganisationDTO implements Serializable{
    private Long idOrganisation;
    private String name;
    private Long fkManager;

    public Long getIdOrganisation() {
        return idOrganisation;
    }

    public void setIdOrganisation(Long idOrganisation) {
        this.idOrganisation = idOrganisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFkManager() {
        return fkManager;
    }

    public void setFkManager(Long fkManager) {
        this.fkManager = fkManager;
    }
    
}
