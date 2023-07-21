/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Esercizio2 {

    private static class Coppia{
        private int x;
        private String q;

        public Coppia(int x, String q){
            this.x = x;
            this.q = q;
        }

        public int getX(){
            return x;
        }

        public String getQ(){
            return q;
        }
    }

    public static void find(ArrayList<Coppia> dati, int a, int b, int s){
        for (Coppia coppia : dati) {
            if(coppia.getX() >= a && coppia.getX() <= b && coppia.getQ().length() <= s){
                System.out.println(coppia.getX() + " " + coppia.getQ());
            }
        }
    }

    public static void print(ArrayList<Coppia> dati, int c){
        for (Coppia coppia : dati) {
            if(coppia.getX() >= c){
                System.out.println(coppia.getX() + " " + coppia.getQ());
            }
        }
    }

    public static ArrayList<Coppia> carica(String file){
        ArrayList<Coppia> dati = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File(file));
            
            while(s.hasNextLine()){
                String line = s.nextLine();
                String[] tokens = line.split(" ");
                Coppia c = new Coppia(Integer.parseInt(tokens[0]), tokens[1]);
                dati.add(c);
            }

            s.close();
        } catch (Exception e) {
            System.out.println("Caricamento del file non andato a buon fine!");
            System.exit(0);
        }

        return dati;
    }
   public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(1);
        }

        ArrayList<Coppia> dati = carica(args[0]);

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("\nScegliere la funzione: \n-Scrivere 1 per la funzione ricerca e stampa delle coppie con a<=x<=b, e lunghezza della stringa <=s. \n-Scrivere 2 per la funzione stampa di tutte le coppie aventi valori del primo elemento x>=c.\n-Scrivere 0 per terminare l'esecuzione del programma.\n");
            int v = scanner.nextInt();
            
            if(v == 0){
                System.exit(0);
            }else if(v == 1){
                System.out.println("Inserire il valore di a: ");
                int a = scanner.nextInt();
                System.out.println("Inserire il valore di b: ");
                int b = scanner.nextInt();
                System.out.println("Inserire il valore di s: ");
                int s = scanner.nextInt();

                find(dati, a, b, s);
            }else if(v == 2){
                System.out.println("Inserire il valore di c: ");
                int c = scanner.nextInt();

                print(dati, c);
            }else{
                System.out.println("Comando non riconosciuto, riprovare!");
            }
        }
   }
}
