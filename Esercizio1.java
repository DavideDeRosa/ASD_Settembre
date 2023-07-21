/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Esercizio1{

    private static class Tree{
        
        private static Nodo radice;

        public Tree(){
            radice = genera();
        }

        private static Nodo genera(){
            Random r = new Random(1054948);
    
            int k = r.nextInt(1000000000) + 1;
            int np = r.nextInt(700) + 1;
            
            return new Nodo(k, np);
        }

        public static Nodo verifica(Nodo n, int k){
            if(n == null || n.getK() == k){
                return n;
            }
            
            if(radice.getK() < k){
                return verifica(n.getRight(), k);
            }

            return verifica(n.getLeft(), k);
        }

        public static void inserisci(Nodo n, Nodo g) {
            if (g.getK() <= n.getK()) { 
                if (n.getLeft() != null) { 
                    inserisci(n.getLeft(), g);
                } else { 
                    n.setLeft(g);
                } 
            } else if (g.getK() > n.getK()) {
                if (n.getRight() != null) { 
                    inserisci(n.getRight(), g);
                } else {
                    n.setRight(g);
                }
            }
          }

        public static int size(Nodo n){
            if(n==null){
                return 0;
            }

            int leftsize = size(n.getLeft());
            int rightsize = size(n.getRight());

            return leftsize + rightsize + 1;
        }

        public static void stampa(Nodo n){
            System.out.println(n);
        }

        public static Nodo getRadice(){
            return radice;
        }
    }

    private static class Nodo{
        private Nodo parent;
        private Nodo left;
        private Nodo right;
        private int k;
        private int np;

        public Nodo(int k, int np){
            parent = null;
            left = null;
            right = null;
            this.k = k;
            this.np = np;
        }

        public Nodo getLeft(){
            return left;
        }

        public Nodo getRight(){
            return right;
        }

        public Nodo getParent(){
            return parent;
        }

       public int getK(){
            return k;
        }

        public int getNp(){
            return np;
        }

        public void setLeft(Nodo t){
            if(left == null){
                left = t;
                t.setParent(this);
            }
        }

        public void setRight(Nodo t){
            if(right == null){
                right = t;
                t.setParent(this);
            }
        }

        public void setParent(Nodo t){
            parent = t;
        }

        public void setK(int k){
            this.k = k;
        }

        public void setNp(int np){
            this.np = np;
        }

        public String toString(){
            return "Chiave: " + k + " Pagine: " + np;
        }
    }

    public static void main(String[] args){
        Tree albero = new Tree();

        for(int i = 0; i < 9999; i++){
            albero.inserisci(albero.getRadice(), albero.genera());
        }

        System.out.println(albero.size(albero.getRadice()));

        Random r = new Random(7105419);

        try {
			PrintWriter pw = new PrintWriter(new File("output1.txt"));

            for(int i = 0; i < 300; i++){
                int k = r.nextInt(1000000000) + 1;
                albero.verifica(albero.getRadice(), k);
                pw.println(k);
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
