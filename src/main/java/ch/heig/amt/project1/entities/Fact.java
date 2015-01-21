/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findAllFact",
            query = "SELECT f FROM Fact f"
    )
})
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Fact implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFact;
    @Column(nullable = false)
    private Boolean isPublic;
    @Column(nullable = false)
    private String type;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Organisation organisation;

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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    
    public abstract Map<String, Object> getProperties();
    
}
