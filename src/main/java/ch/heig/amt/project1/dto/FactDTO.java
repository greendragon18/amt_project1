/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import java.io.Serializable;

/**
 *
 * @author bradock
 */
public abstract class FactDTO implements Serializable{
    private Long idFact;
    private Boolean isPublic;
    private Long fkOrganisation;

    public Long getIdFact() {
        return idFact;
    }

    public void setIdFact(Long idFact) {
        this.idFact = idFact;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getFkOrganisation() {
        return fkOrganisation;
    }

    public void setFkOrganisation(Long fkOrganisation) {
        this.fkOrganisation = fkOrganisation;
    }
    
}
