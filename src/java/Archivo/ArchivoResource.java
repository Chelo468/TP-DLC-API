/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Chelo
 */
public class ArchivoResource {

    private String name;

    /**
     * Creates a new instance of ArchivoResource
     */
    private ArchivoResource(String name) {
        this.name = name;
    }

    /**
     * Get instance of the ArchivoResource
     */
    public static ArchivoResource getInstance(String name) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ArchivoResource class.
        return new ArchivoResource(name);
    }

    /**
     * Retrieves representation of an instance of Archivo.ArchivoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return "{\"sarasa\": \"" + this.name + "\"}";
    }

    /**
     * PUT method for updating or creating an instance of ArchivoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource ArchivoResource
     */
    @DELETE
    public void delete() {
    }
}
