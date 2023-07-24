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
 * Considerazioni e richieste extra:
 * ciao
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Esercizio4 {

    private static List<Edge> edges = new LinkedList<>();
    private static int n = 12;
    private static int m = 15;
    private static int[] p;
    private static double[] d;
    private static Edge[] sp;
    private static int source;
    private static HashMap<String, Integer> nodi = new HashMap<>();
    private static ArrayList<Double> weights = new ArrayList<>();

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
                
                if (d[src] + w < d[dst]) {
                    d[dst] = d[src] + w;
                    p[dst] = src;
                    sp[dst] = e;
                }
            }
        }
        
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

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(1);
        }

        readInputFile(args[0]);

        for(int i = 0; i < n; i++){
            source = i;
            if (shortestPaths(i)) {
            System.err.println("Negative cycles detected");
            } else {
                print_paths();
            }
        }
    }
}
