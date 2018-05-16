/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import Entidades.SAC;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Chelo
 */
public class IndexadorResource {

    private String id;
    private String urlFrom;
    private String urlTo;

    /**
     * Creates a new instance of IndexadorResource
     */
    private IndexadorResource(String id) {
        this.id = id;
        this.urlFrom = "C:\\Users\\Chelo\\Documents\\NetBeansProjects\\TP-API\\documentos\\para subir\\";
        this.urlTo = "C:\\Users\\Chelo\\Documents\\NetBeansProjects\\TP-API\\documentos\\subidos\\";
    }

    /**
     * Get instance of the IndexadorResource
     */
    public static IndexadorResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of IndexadorResource class.
        return new IndexadorResource(id);
    }

    /**
     * Retrieves representation of an instance of Indexador.IndexadorResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        try
        {
            SAC.cargarIndexadorBD();
            SAC.copyFileStream(urlFrom, urlTo);
        }
        catch(Exception ex)
        {
            return "{\"Error\": true, \"Mensaje\":\""+ ex.toString() +"\"}";
        }
        
        return "{\"Error\": false, \"Mensaje\":\"La indexación finalizó con éxito\"}";
    }

    /**
     * PUT method for updating or creating an instance of IndexadorResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource IndexadorResource
     */
    @DELETE
    public void delete() {
    }
}
