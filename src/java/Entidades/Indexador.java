/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Indexador se usa con el patron Singleton para proveer solo una instancia de
 * esta clase.
 */
public class Indexador {

    //Usamos un Map que almacena <nombrePalabra, Palabra>
    private Map<String, Palabra> indexador;

    // private static  Diccionario instance;
    private static Indexador instance = null;

    //Cantidad de documentos indexados
    private int documentosIndexados;

    private static final Integer VALOR_UNO = 1;

    private Indexador() {
        indexador = new HashMap<String, Palabra>();
    }

    public static Indexador getInstance() {

        if (instance == null) {
            instance = new Indexador();
        }
        return instance;

    }

    public void indexarPalabras(Archivo archivoProcesar) throws IOException {

        AccesoFile File = new AccesoFile();

        BufferedReader reader = File.leerArchivo(archivoProcesar.getRutaFile());
        String nombreDocumento = archivoProcesar.getNombreFile();

        //Si el documento se leyo aumentamos el contador
        setDocumentosIndexados(getDocumentosIndexados() + VALOR_UNO);

        //inputLine es la linea ha ser leida 
        String inputLine = null;

        while ((inputLine = reader.readLine()) != null) {
            //Expresión regular para parsear la linea  
            inputLine = inputLine.replaceAll("[^a-zA-ZÁÉÍÓÚáéíóúÑñÜü]", " ").toLowerCase();
            //Separamos todas las palabras de la linea
            String[] words = inputLine.split("\\s+");

            // Ignoramos si quedan espacios entre filas .
            if (inputLine.equals("")) {
                continue;
            }

            //Recorremos palabra por palabra 
            for (String word : words) {

                //Ignoramos texto vacio despues del  parse
                if (word.equals("")) {
                    continue;
                }

                //Traemos la palabra del indexador
                Palabra palabra = indexador.get(word);
                if (palabra == null) {
                    //Si la palabra no existe en el vocabulario, la agregamos
                    Palabra nuevaPalabra = new Palabra();
                    
                    //Le decimos al vocabulario que la palabra está en este documento
                    nuevaPalabra.agregarDocumento(nombreDocumento);
                    
                    //Agregamos la palabra al indexador
                    indexador.put(word, nuevaPalabra);

                } else {

                    //La palabra ya existe en el indexador
                    
                    //Informamos que la palabra tambien esta en el documento
                    //Si ya estaba suma uno a la cantidad y sino la agrega
                    palabra.indexarDocumento(nombreDocumento);
                    
                    //Agregamos uno mas al indexador
                    indexador.put(word, palabra);

                }

            }

        }

    }

    public void cargarIndexadorBD() {
        int count = 0;
        int guardados = 1;
        AccesoBD BD = new AccesoBD();
        ResultSet resultSet = BD.leerDatos();
        try {
            while (resultSet.next()) {
                String rsPalabra = resultSet.getString("palabra");
                int rsRepGlobal = resultSet.getInt("repglobal");
                String rsDocumento = resultSet.getString("documento");
                int rsRepDocumento = resultSet.getInt("repdocumento");
                //Traemos la palabra del indexador

                Palabra palabra = indexador.get(rsPalabra);
                if (palabra == null) {

                    Palabra nuevaPalabra = new Palabra();
                    nuevaPalabra.setRepeticiones(rsRepGlobal);
                    nuevaPalabra.indexarDocBD(rsDocumento, rsRepDocumento);
                    indexador.put(rsPalabra, nuevaPalabra);

                } else {

                    palabra.indexarDocBD(rsDocumento, rsRepDocumento);
                    indexador.put(rsPalabra, palabra);

                }
                if (count >= (10000 * guardados)) {
                    System.out.println("Se leyeron : " + count);
                    guardados++;
                }

                count++;
            }
            resultSet = BD.leerDocumentos();
            int documentos = 0;
            while (resultSet.next()) {
                documentos = resultSet.getInt("documentos");

            }

            this.setDocumentosIndexados(documentos);
            System.out.println("Se cargaron Docs:" + documentos);

            System.out.println("Se cargaron en total : " + count);

        } catch (SQLException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getDocumentosBD() {
        AccesoBD BD = new AccesoBD();

    }

    public Map<String, Palabra> getIndexador() {
        return indexador;
    }

    public void setIndexador(Map<String, Palabra> indexador) {
        this.indexador = indexador;
    }

    public int getDocumentosIndexados() {
        return documentosIndexados;
    }

    public void setDocumentosIndexados(int documentosIndexados) {
        this.documentosIndexados = documentosIndexados;
    }

    public Palabra retornarPalabra(String palabra) {

        return indexador.get(palabra);

    }

}
