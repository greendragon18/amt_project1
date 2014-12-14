/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alain Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findAllOrganisation",
            query = "SELECT o FROM Organisation o"
    )
})
public class Organisation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOrganisation;
    @Column(nullable = false)
    private String name;
    
    @OneToMany(targetEntity=User.class, 
        fetch = FetchType.LAZY, 
        cascade = CascadeType.ALL, 
        mappedBy="organisation")
    private List<User> users;
    
    @OneToOne
    private User manager;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

}
