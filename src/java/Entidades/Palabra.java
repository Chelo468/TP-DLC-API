/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matyas
 */
public class Palabra {
    
    //Cuenta las repeticiones a nivel global de la palabra
    private int repeticiones;
   
    //HASHMAP: NombreDoc, Contador
    private Map<String, Integer> documentos;
    
    private static final Integer VALOR_UNO = 1;

    public Palabra() {
    
      documentos = new HashMap<String, Integer>();
      this.repeticiones = VALOR_UNO;
    }

//Busca la palabra en el hash y no la encuentra:
//Agrega la palabra al hash principal, y agrega el documenta 
// y la repetición en el hash secundario.
    public void agregarDocumento(String documento) {
        documentos.put(documento, VALOR_UNO);
        
    }

// 2)	Busca la palabra y existe en el hash: 
//
//2.1)	Busca si el documento existe en el hash documentos:
//
//-Si existe el documento, le suma uno a las repeticiones
//-Si no existe el documento lo agrega, y pone el contador en uno.
    public void indexarDocumento(String documento) {
       
        setRepeticiones(getRepeticiones() + VALOR_UNO);
        //Integer contador = 0;
       
        Integer contador = documentos.get(documento);
       
        if (contador == null) {
            agregarDocumento(documento);
        }else{
            documentos.put(documento, contador + VALOR_UNO);
        }

    }
    
    public void indexarDocBD(String documento, int contador){
    
    
    documentos.put(documento, contador);
    
    
    
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public double devolverFrecuenciaInversa(int cantidadDocumentosTotal){
    
    double frecuenciaInversa;
    //Cantidad de documentos en los que aparece el término tr.
    int terminoDocumentos = documentos.size();
    
    //Frecuencia 
    frecuenciaInversa = Math.log10((double)cantidadDocumentosTotal/(double)terminoDocumentos);
  
    return frecuenciaInversa;
    }

    public Map<String, Integer> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Map<String, Integer> documentos) {
        this.documentos = documentos;
    }
    
    

}
