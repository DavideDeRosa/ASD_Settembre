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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Esercizio5 {

    private static List<Edge> edges = new LinkedList<>();
    private static int n = 12;
    private static int m = 15;
    private static HashMap<String, Integer> nodi = new HashMap<>();
    private static ArrayList<Double> weights = new ArrayList<>();
    private static double[][] d;
    private static int[][] next;

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

    private static void readInputFile(String file){
        try{
            Scanner s = new Scanner(new File(file));

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
                weights.add(preInstalledCapacity);
                double weight = maxPreInstalledCapacity() / preInstalledCapacity;

                edges.add(new Edge(sourceNode, targetNode, weight));
                edges.add(new Edge(targetNode, sourceNode, weight));
            }

            s.close();
        }catch(Exception e){
            System.out.println("Caricamento del file non andato a buon fine!");
            System.exit(0);
        }
    }

    private static double maxPreInstalledCapacity() {
        double maxCapacity = Double.MIN_VALUE;
        for (Double d : weights) {
            maxCapacity = Math.max(maxCapacity, d);
        }
        return maxCapacity;
    }

    private static void shortestPaths(){
        int u, v, k;
	    d = new double[n][n];
        next = new int[n][n];
        
        // Initialize distance matrix 
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
		// the distance between node u and node v without passing through any intermediate node            
            d[u][v] = w;
            next[u][v] = v;
        }
        // Main loop of the Floyd-Warshall algorithm
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
        // Check for cycles of negative weight
        for (u=0; u<n; u++) {
            if (d[u][u] < 0.0) {
                System.out.println("Warning: negative-weight cycle(s) detected");
                break;
            }
        }        
    }

    private static void printPath(int u, int v){
	if ( (u != v) && (next[u][v] < 0) ) {
            System.out.print("Not reachable");
        } else {
            System.out.print(u);
            while ( u != v ) {
                u = next[u][v];
                System.out.printf("->%d", u);
            }
        }
    }

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
            System.exit(1);
        }

        readInputFile(args[0]);

        shortestPaths();
        printPaths();
    }
}
