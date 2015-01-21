/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
public class FactDTO implements Serializable{
    private Long idFact;
    private Boolean isPublic;
    private Long idOrganisation;
    private String type;
    @JsonProperty("properties")
    private Map<String, Object> properties;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Long getIdOrganisation() {
        return idOrganisation;
    }

    public void setIdOrganisation(Long fkOrganisation) {
        this.idOrganisation = fkOrganisation;
    }
}
