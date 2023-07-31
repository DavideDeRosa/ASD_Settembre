/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 * 
 * Esecuzione:
 * Per compilare: javac Esercizio5.java
 * Per eseguire: java Esercizio5 <file input>
 * 
 * Considerazioni e richieste extra:
 * ciao
 */

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Esercizio5 {

    private static List<Edge> edges = new LinkedList<>(); //il grafo viene rappresentato come una List di Edge
    private static int n = 12; //numero di nodi
    private static int m = 15; //numero di archi
    private static HashMap<String, Integer> nodi = new HashMap<>(); //HashMap utilizzata per convertire il nome del nodo in un indice numerico
    private static double[][] d; //matrice delle distanze
    private static int[][] next; //next[u][v] e' il successivo del nodo 'u' nel cammino minimo per il nodo 'v'

    /*
     * Creazione classe Edge
     */
    private static class Edge{
        final int src;
        final int dst;
        final double w;

        public Edge(int src, int dst, double w){
            this.src = src;
            this.dst = dst;
            this.w = w;
        }
    }

    /*
     * Viene effettuato il caricamento dei dati da File, inserendo gli archi nella LinkedList apposita(edges).
     */
    private static void letturaFile(String file){
        double maxCapacity = -1;

        try{
            Scanner s = new Scanner(new File(file));

            /*
             * Vengono fatte scorrere le Line fino alla stringa 'NODES ('. Successivamente vengono caricati i 12 nodi in una HashMap,
             * che ci permette di assegnare ad ogni nodo un indice da 0 a 11.
             */
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.equals("NODES (")) {
                break;
                }
            }

            for(int i = 0; i < n; i++){
                String line = s.nextLine().trim();
                String[] parts = line.split(" ");

                nodi.put(parts[0], i);
            }

            /*
             * Vengono fatte scorrere le Line fino alla stringa 'LINKS ('. Successivamente vengono caricati soltanto i dati utili 
             * alla creazione degli archi.
             * Nota Bene: essendo un grafo con archi bidirezionali, vengono aggiunti due archi per volta, con source e target invertiti.
             */
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.equals("LINKS (")) {
                    break;
                }
            }

            for (int i = 0; i < m; i++) {
                String line = s.nextLine().trim();
                String[] parts = line.split(" ");
                int sourceNode = nodi.get(parts[2]);
                int targetNode = nodi.get(parts[3]);
                double preInstalledCapacity = Double.parseDouble(parts[5]);

                if(maxCapacity < preInstalledCapacity){
                    maxCapacity = preInstalledCapacity;
                }
                
                double weight = maxCapacity / preInstalledCapacity;

                edges.add(new Edge(sourceNode, targetNode, weight));
                edges.add(new Edge(targetNode, sourceNode, weight));
            }

            /*
             * Viene chiuso lo Scanner utilizzato per la lettura del file di input.
             */
            s.close();
        }catch(Exception e){
            System.out.println("Caricamento del file non andato a buon fine!");
            System.exit(0);
        }
    }

    /*
     * Viene implementato l'algoritmo di Floyd-Warshall.
     */
    private static void shortestPaths(){
        int u, v, k;
	    d = new double[n][n];
        next = new int[n][n];
        
        /*
         * Viene inizializzata la matrice delle distanze e la matrice next.
         */
        for (u=0; u<n; u++) {
            for (v=0; v<n; v++) {
                d[u][v] = (u == v ? 0 : Double.POSITIVE_INFINITY);
                next[u][v] = (u == v ? u : -1);
            }
        }

        for (final Edge edge : edges) {
            final double w = edge.w;                        
            u = edge.src;
            v = edge.dst;

            d[u][v] = w; //distanza tra il nodo 'u' e il nodo 'v' senza passare attraverso altri nodi intermedi
            next[u][v] = v;
        }
        
        /*
         * Viene implementata la parte principale dell'algoritmo di Floyd-Warshall.
         */
        for (k=0; k<n; k++) {
            for (u=0; u<n; u++) {
                for (v=0; v<n; v++) {
                    if (d[u][k] + d[k][v] < d[u][v]) {
                        d[u][v] = d[u][k] + d[k][v];
                        next[u][v] = next[u][k];
                    }
                }
            }
        }

        /*
         * Viene effettuato il controllo per la presenza di cicli negativi.
         */
        for (u=0; u<n; u++) {
            if (d[u][u] < 0.0) {
                System.out.println("Trovato ciclo negativo!");
                break;
            }
        }        
    }

    /*
     * Viene implementato un metodo che stampa il percorso minimo tra due nodi, se esiste.
     */
    private static void printPath(int u, int v){
	if ( (u != v) && (next[u][v] < 0) ) {
            System.out.print("Irraggiungibile");
        } else {
            System.out.print(u);
            while ( u != v ) {
                u = next[u][v];
                System.out.printf("->%d", u);
            }
        }
    }

    /*
     * Vengono stampati i cammini minimi come richiesto dalla traccia.
     */
    private static void printPaths(){
        int i, j;
        for (i=0; i<n; i++) {
            System.out.println();
            System.out.println("   s    d         dist path");
            System.out.println("---- ---- ------------ -------------------");
            for (j=0; j<n; j++) {
                System.out.printf("%4d %4d %12.5f ", i, j, d[i][j]);
                printPath(i, j);
                System.out.println();
            }
        }
    }    

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(0);
        }

        letturaFile(args[0]);

        /*
         * Viene eseguito l'algoritmo di Floyd-Warshall per calcolare i cammini minimi da ogni nodo verso ogni nodo.
         */
        shortestPaths();
        printPaths();
    }
}
