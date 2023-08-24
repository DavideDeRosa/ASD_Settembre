/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 * 
 * Esecuzione:
 * Per compilare: javac Esercizio4.java
 * Per eseguire: java Esercizio4 <file input>
 * 
 * File di Input:
 * Viene utilizzato un File di input come specificato nella consegna, nel suo formato esteso.
 * 
 * Considerazioni e richieste extra:
 * ciao
 */

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Esercizio4 {

    private static List<Edge> edges = new LinkedList<>(); //il grafo viene rappresentato come una List di Edge
    private static int n = 12; //numero di nodi
    private static int m = 15; //numero di archi
    private static int[] p; //array dei parents
    private static double[] d; //array delle distanze dall'origine
    private static Edge[] sp; //sp[v] e' l'arco che collega v e il suo genitore p[v]
    private static int source; 
    private static HashMap<String, Integer> nodi = new HashMap<>(); //HashMap utilizzata per convertire il nome del nodo in un indice numerico

    /*
     * Creazione classe Edge
     */
    private static class Edge{
        final int src;
        final int dst;
        double w;

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
                         
                /*
                 * Vengono salvati gli archi con un peso momentaneo, per poi effettuare successivamente il calcolo del peso.
                 */
                edges.add(new Edge(sourceNode, targetNode, preInstalledCapacity));
                edges.add(new Edge(targetNode, sourceNode, preInstalledCapacity));
            }

            /*
             * Viene effettuato il calcolo corretto del peso per ogni arco.
             */
            for (Edge e : edges) {
                e.w = maxCapacity / e.w;
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
     * Viene implementato l'algoritmo di Bellman-Ford, usando il nodo 's' come source.
     */
    private static boolean shortestPaths(int s){
	    d = new double[n];
        p = new int[n];
        sp = new Edge[n];

        Arrays.fill(d, Double.POSITIVE_INFINITY);
        Arrays.fill(p, -1);

	    d[s] = 0.0;
        for (int i=0; i<n-1; i++) {
            for (Edge e: edges) {
                final int src = e.src;
                final int dst = e.dst;
                final double w = e.w;
                
                /*
                 * Tecnica del rilassamento, fondamentale per l'algoritmo di Bellman-Ford.
                 */
                if (d[src] + w < d[dst]) {
                    d[dst] = d[src] + w;
                    p[dst] = src;
                    sp[dst] = e;
                }
            }
        }
        
        /*
         * Viene effettuato il controllo per la presenza di cicli negativi.
         */
        for (Edge e: edges) {
            final int src = e.src;
            final int dst = e.dst;
            final double w = e.w;

            if (d[src] + w < d[dst]) {
                return true;
            }
        }

        return false;
    }

    /*
     * Viene implementato un metodo che stampa il percorso minimo tra due nodi (source e dst), se esiste.
     */
    private static void print_path(int dst){
        if (dst == source)
            System.out.print(dst);
        else if (p[dst] < 0)
            System.out.print("Irraggiungibile");
        else {
            print_path(p[dst]);
            System.out.print("->" + dst);
        }
    }

    /*
     * Viene stampato il cammino minimo come richiesto dalla traccia, tra source e tutti i nodi.
     */
    private static void print_paths(){
        System.out.println();
        System.out.println("   s    d         dist path");
        System.out.println("---- ---- ------------ -------------------");
        for (int dst=0; dst<n; dst++) {
            System.out.printf("%4d %4d %12.4f ", source, dst, d[dst]);
            print_path(dst);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(0);
        }

        letturaFile(args[0]);

        /*
         * Viene eseguito l'algoritmo di Bellman-Ford per calcolare i cammini minimi da ogni nodo verso ogni nodo.
         * Se viene rilevato un ciclo negativo, viene mostrato un messaggio di errore.
         */
        for(int i = 0; i < n; i++){
            source = i;
            if (shortestPaths(i)) {
                System.err.println("Trovato ciclo negativo!");
            } else {
                print_paths();
            }
        }
    }
}
