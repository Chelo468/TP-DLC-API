/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
//import utn.frc.dlc.sac.db.DBManager;

/**
 *
 * @author scarafia
 */
public abstract class SAC {

    // DATABASE
//    private static String DBUrl = "jdbc:postgresql://localhost:5432/dlcdb";
//    private static String DBUserName = "dlcusr";
//    private static String DBPassword = "dlcpwd";
//
//    private static String DBResourceName = "jdbc/pg_dlcdb";
//
//    public static DBManager getSingleDB() throws Exception {
//        DBManager db = new DBManager();
//        db.setConnectionMode(DBManager.SINGLECONNECTIONMODE);
//        db.setDriverName(DBManager.POSTGRESDRIVERNAME);
//        db.setUrl(DBUrl);
//        db.setUserName(DBUserName);
//        db.setPassword(DBPassword);
//        db.connect();
//        return db;
//    }
//
//    public static DBManager getPoolDB() throws Exception {
//        DBManager db = new DBManager();
//        db.setConnectionMode(DBManager.POOLCONNECTIONMODE);
//        db.setResourceName(DBResourceName);
//        db.connect();
//        return db;
//    }
    // HELPER FUNCTIONS
    private static int sequence = 0;
    private static String urlFrom;
    private static String urlTo;

    public static int nextID() {
        return ++sequence;
    }
    
    public static void guardarDatosBD(){
        AccesoBD BD = new AccesoBD();
        BD.guardarDatos();
    
    }
    
    public static void cargarIndexadorBD(){
        if (Indexador.getInstance().getDocumentosIndexados() == 0) {
            Indexador.getInstance().cargarIndexadorBD();
            //guardarDatosBD();
        }
        
    }

    //Indexar documentos desde filesystem
    public static void indexarDocumentosFS() {
        
        //Este path se debe cambiar con el del servidor de cada uno
        //if (Indexador.getInstance().getDocumentosIndexados() != 0) {
        if(urlFrom != ""){
            Path dir = Paths.get(urlFrom);//"C:\\Users\\Benjamin Franklin\\UTN\\DLC\\TPI\\TPIntegradorDLC\\web\\documentos"
            try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(dir)) {
                int contador = 1;
                for (Path fileS : stream) {

                    Archivo file = new Archivo(fileS.toString(), fileS.getFileName().toString());
                    Indexador.getInstance().indexarPalabras(file);

                    System.out.println(file.toString() + " : " + contador);
                    contador++;
                }
            } catch (IOException | DirectoryIteratorException x) {
                // IOException can never be thrown by the iteration.
                // In this snippet, it can only be thrown by newDirectoryStream.
                System.err.println(x);
            }
        }
        //}

    }
    
    //Indexar documentos desde Base de datos
    public static void indexarDocumentos() {
        //if (Indexador.getInstance().getDocumentosIndexados() == 0) {
            
            //Carga en memoria 
            Indexador.getInstance().cargarIndexadorBD();
        //}

    }

    public static void indexarNuevoDocumento(String name) {
        if (name != null) {
            //Path dir = Paths.get("C:\\Users\\Benjamin Franklin\\UTN\\DLC\\TPI\\TPIntegradorDLC\\documentos");
            Path fileS = Paths.get(name);
            try {
                Archivo file = new Archivo(fileS.toString(), fileS.getFileName().toString());

                Indexador.getInstance().indexarPalabras(file);
            } catch (IOException | DirectoryIteratorException x) {
                // IOException can never be thrown by the iteration.
                // In this snippet, it can only be thrown by newDirectoryStream.
                System.err.println(x);
            }
        }
    }

    public static void copyFileStream(String sourcePath, String pathDestino) throws IOException {

        if (sourcePath != null) {
            urlFrom = sourcePath;
            urlTo = pathDestino;
            InputStream is = null;
            OutputStream os = null;
            
            //Agregado
            File dir = new File(sourcePath);
            String[] ficheros = dir.list();
            String nombreFichero;
            String pathArchivo;
            File source;
            File dest;
            for (int i = 0; i < ficheros.length; i++) {
                nombreFichero = ficheros[i];
                pathArchivo = sourcePath + nombreFichero;
                
                source = new File(pathArchivo);
                dest = new File(pathDestino + source.getName());
                if(!dest.exists())
                {
                    try 
                    {
                        is = new FileInputStream(source);
                        os = new FileOutputStream(dest);
                        byte[] buffer = new byte[(int)source.length()];
                        int length;
                        //while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, buffer.length);
                        
                        //}
                    } finally {
                        is.close();
                        os.close();
                    }

                    indexarNuevoDocumento(dest.getAbsolutePath());
                }
                
            }
            
            AccesoBD bd = new AccesoBD();
            bd.guardarDatos();
        }
    }

    private static void almacenarIndexador(Indexador indexador) {
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
