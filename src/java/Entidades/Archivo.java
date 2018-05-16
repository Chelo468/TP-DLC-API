/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Matyas
 */
public class Archivo {
     private String rutaFile;
    private String NombreFile;

    public Archivo(String rutaFile, String NombreFile) {
        this.rutaFile = rutaFile;
        this.NombreFile = NombreFile;
    }

    public Archivo() {
    }

    public String getRutaFile() {
        return rutaFile;
    }

    public void setRutaFile(String rutaFile) {
        this.rutaFile = rutaFile;
    }

    public String getNombreFile() {
        return NombreFile;
    }

    public void setNombreFile(String NombreFile) {
        this.NombreFile = NombreFile;
    }
    
    
}
