/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.dao;

import ch.heig.amt.project1.dto.UserDTO;
import ch.heig.amt.project1.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bradock
 */
@Local
public interface UserDaoLocal {

    public void create(User user);

    public void update(User user);

    public void delete(User user);

    public User findById(Long id);

    public List<User> findAllUserOfAnOrganisation(Long idOrganisation);

    public UserDTO entityToDTO(User user);

    public User dtoToNewEntity(UserDTO userDTO, Long idOrganisation) throws Exception;

    public User dtoToEntity(UserDTO userDTO, Long idOrganisation) throws Exception;
    
}
