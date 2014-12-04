/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Butticaz Leal Nicolas & Piere-Alin Curty
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findAllUserOfAnOrganisation",
            query = "SELECT u FROM User u WHERE u.organisation.idOrganisation=:idOrganisation"
    )
})
@Table(name = "PROJECT_USER")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
    @ManyToOne
    private Organisation organisation;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    
}
