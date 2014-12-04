/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heig.amt.project1.api;

import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author bradock
 */
@Path("facts")
@Stateless
public class FactResource {
    @Context
    private UriInfo context;
}
