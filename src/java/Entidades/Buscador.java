/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Matyas
 */
public class Buscador {

    //String va ser el nombre del documento con su respectivo ranking
    Map<Double, String> ranking;
    ArrayList palabrasOmitidas;
    Palabra palabra;
    int documentosTotales;
    Double numeroRanking;
//    CompararValores compValores;
    HashMap<String, Double> rankingProvisorio;

    public Buscador() {
        rankingProvisorio = new HashMap<String, Double>();
//        compValores = new CompararValores(rankingProvisorio);
        ranking = new TreeMap<Double, String>(new ComparadorPropio());

    }

    public Map<Double, String> devolverRanking(String frase) {

        if (frase != null) {

            frase = frase.replaceAll("[^a-zA-ZÁÉÍÓÚáéíóúÑñÜü]", " ").toLowerCase();
            //Divido la frase en palabras
            String[] palabras = frase.split("\\s+");

            palabrasOmitidas = new ArrayList();
            documentosTotales = Indexador.getInstance().getDocumentosIndexados();

            Double frecuenciaInversa;

            for (int i = 0; i < palabras.length; i++) {

                String palabraActual = palabras[i];

                palabra = Indexador.getInstance().retornarPalabra(palabraActual);

                //Validaciones
                if (palabra == null) {
                    palabrasOmitidas.add(palabraActual);
                    System.out.println("palabra no encontrada: " + palabraActual);
                    continue;
                }

                frecuenciaInversa = palabra.devolverFrecuenciaInversa(documentosTotales);

                //El 0 se puede cambiar por 0.2 0.3 etc dependiendo el filtro de stop words que querramos hacer.
                if (frecuenciaInversa == 0) {
                    //palabrasOmitidas.add(palabraActual);
                    System.out.println("palabra stopwords: " + palabraActual);
                    //continue;
                }

                //Validaciones
                //---------------------------------------------------
                //Llama al iterador del hashmap del documento
                System.out.println("***********************************************");
                System.out.println("PALABRA: " + palabraActual + " | FRECUENCIAINV: " + frecuenciaInversa);
                System.out.println("***********************************************");
                System.out.println("DOCUMENTO | REPETICIONES  | REPT*FRECUEINVER | *");
                System.out.println("***********************************************");
                Iterator it = palabra.getDocumentos().entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    System.out.println(pair.getKey() + " = " + (Integer) pair.getValue() + " : "
                            + ((Integer) pair.getValue() * frecuenciaInversa));

                    String nombreDoc = pair.getKey().toString();
                    int cantidadRepeticiones = (Integer) pair.getValue();
                    //TermFrequency*InverseFrequency
                    Double calificacion = cantidadRepeticiones * frecuenciaInversa;

                    numeroRanking = rankingProvisorio.get(nombreDoc);

                    if (numeroRanking == null) {

                        rankingProvisorio.put(nombreDoc, calificacion);

                    } else {

                        numeroRanking = numeroRanking + calificacion;
                        rankingProvisorio.put(nombreDoc, numeroRanking);

                    }

                }

            }

        }

        Iterator rankingPro = rankingProvisorio.entrySet().iterator();
        while (rankingPro.hasNext()) {
            Map.Entry pair = (Map.Entry) rankingPro.next();
            ranking.put((Double) pair.getValue(), (String) pair.getKey());

        }

        System.out.println("results: " + ranking);

        return ranking;
    }
//Clase interna para comparar los valores del ranking

    private class ComparadorPropio implements Comparator<Double> {

        @Override
        public int compare(Double e1, Double e2) {
            if (e1 < e2) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
