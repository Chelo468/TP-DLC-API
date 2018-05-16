/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matyas
 */
public class AccesoBD {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/integradordlc";
    Connection conn = null;
    ResultSet rs = null;
    Statement stat = null;

    public AccesoBD() {
        // conectar();
    }

    private void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("Se conecto con la BD");
        } catch (Exception ex) {
            System.out.println("Hubo un error al conectarse con la BD " + ex);
        }

    }

    public ResultSet leerDatos() {

        try {
            conectar();
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from indexado;");

        } catch (SQLException ex) {
            System.out.println("Error al leer los datos de la BD " + ex.getMessage());
        }

        return rs;
    }

    public void borrarDatos() {

        try {
            conectar();
            stat = conn.createStatement();
            rs = stat.executeQuery("delete from indexado;");
            cerrarBD();
        } catch (SQLException ex) {
            System.out.println("Error al borrar la BD " + ex.getMessage());
        }

    }

    public int guardarDatos() {
        int count = 0;
        int guardados = 1;
        try {

            conectar();
            String sql = "INSERT INTO indexado(palabra, repglobal,documento,repdocumento) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            Palabra pal;
            Iterator it1 = Indexador.getInstance().getIndexador().entrySet().iterator();

            while (it1.hasNext()) {

                Map.Entry words = (Map.Entry) it1.next();

                String palabra = (String) words.getKey();

                pal = (Palabra) words.getValue();

                int repeticionGlobal = pal.getRepeticiones();
                Iterator it2 = pal.getDocumentos().entrySet().iterator();

                while (it2.hasNext()) {

                    Map.Entry docs = (Map.Entry) it2.next();
                    String documento = (String) docs.getKey();
                    int repeticionesDoc = (int) docs.getValue();
                    pstmt.setString(1, palabra);
                    pstmt.setInt(2, repeticionGlobal);
                    pstmt.setString(3, documento);
                    pstmt.setInt(4, repeticionesDoc);
                    pstmt.addBatch();

                }

                if (count >= (10000 * guardados)) {
                    pstmt.executeBatch();
                    System.out.println("Se almacenaron: " + count);
                    guardados++;
                }

                count++;

            }
            pstmt.executeBatch();
            conn.setAutoCommit(true);
            System.out.println("Se cargaron los datos" + count);
            pstmt.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error al guardar los datos a la BD " + ex.getMessage());
        }
        return count;
    }

    public void cerrarBD() {

        try {
            rs.close();
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la BD " + ex.getMessage());
        }

    }

    public ResultSet leerDocumentos() {

        try {
            conectar();
            stat = conn.createStatement();
            rs = stat.executeQuery("select count(distinct(documento))as documentos from indexado ;");

        } catch (SQLException ex) {
            System.out.println("Error al leer los datos de la BD " + ex.getMessage());
        }

        return rs;
    }
}
