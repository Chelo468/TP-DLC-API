/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Chelo
 */
@Path("/buscar")
public class ArchivoesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ArchivoesResource
     */
    public ArchivoesResource() {
    }

    /**
     * Retrieves representation of an instance of Archivo.ArchivoesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Sub-resource locator method for {name}
     */
    @Path("{name}")
    public ArchivoResource getArchivoResource(@PathParam("name") String name) {
        return ArchivoResource.getInstance(name);
    }
}
