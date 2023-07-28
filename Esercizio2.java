/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 * 
 * Esecuzione:
 * Per compilare: javac Esercizio2.java
 * Per eseguire: java Esercizio2 <file input>
 * Una volta avviato correttamente verrà mostrato un menù, scrivendo '1' e premendo invio si accederà alla prima funzione
 * richiesta. Verranno successivamente richiesti i valori di 'a', 'b' e 's', uno alla volta. Verrà poi effettuata la stampa.
 * Scrivendo '2', verrà richiesto il valore di 'c', per poi mostrare a schermo il risultato. Con '0' si termina l'esecuzione.
 * 
 * Considerazioni e richieste extra:
 * Ho scelto di implementare il tutto attraverso l'utilizzo di un ArrayList di "Coppia", classe creata appositamente per mantenere
 * i dati inerenti alla coppia. Una volta caricate le diverse coppie dal file di input, viene mostrato all'utente un elenco di funzioni.
 * La prima funzione richiede tre valori (a, b, s), che permettono successivamente di richiamare il metodo "find()". 
 * Il metodo find() scorre tutta la lista per stampare ogni coppia che abbia il valore della chiave compreso tra a e b e la lunghezza
 * della stringa minore o uguale a s. Il costo computazionale di questo metodo è O(n), dovendo scorrere tutti gli elementi della lista.
 * La seconda funzione richiede un valore (c), che permette successivamente di richiamare il metodo "print()".
 * Il metodo print() scorre tutta la lista per stamapre ogni coppia che abbia il valore della chiave maggiore o uguale a c.
 * Il costo computazionale di questo metodo è O(n), dovendo scorrere tutti gli elementi della lista.
 * Per concludere, la funzione che carica tutte le coppie dal file di input ha costo computazionale O(n).
 * 
 * Il costo computazionale della soluzione è quindi O(n).
 * 
 * Altre possibili implementazioni prevedevano l'utilizzo di MinHeap o HashMap, che avrebbero ridotto il costo computazionale di alcune
 * operazioni, ma aumentato il costo dell'inserimento o di altre operazioni nei casi pessimi.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Esercizio2 {

    /*
     * Creazione classe Coppia
     */
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

    /*
     * Viene implementata l'operazione di "ricerca e stampa delle coppie con a≤x≤b, e lunghezza della stringa ≤s".
     * Vengono quindi stampate tutte le coppie aventi chiave con valore compreso tra a e b, 
     * e con lunghezza della stringa minore o uguale di s.
     */
    public static void find(ArrayList<Coppia> dati, int a, int b, int s){
        for (Coppia coppia : dati) {
            if(coppia.getX() >= a && coppia.getX() <= b && coppia.getQ().length() <= s){
                System.out.println(coppia.getX() + " " + coppia.getQ());
            }
        }
    }

    /*
     * Viene implementata l'operazione di "stampa di tutte le coppie aventi valori del primo elemento x≥c".
     * Vengono quindi stampate tutte le coppie aventi chiave con valore maggiore della variabile c.
     */
    public static void print(ArrayList<Coppia> dati, int c){
        for (Coppia coppia : dati) {
            if(coppia.getX() >= c){
                System.out.println(coppia.getX() + " " + coppia.getQ());
            }
        }
    }
    
    /*
     * Viene effettuato il caricamento dei dati da File, creando una Coppia per ogni riga contenuta nel file dato in input.
     * Una volta creata la Coppia, viene inserita in un ArrayList.
     */
    public static ArrayList<Coppia> letturaFile(String file){
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

        ArrayList<Coppia> dati = letturaFile(args[0]);

        Scanner scanner = new Scanner(System.in);

        while(true){
            /*
             * Vengono effettuate le stampe e le letture da tastiera per gestire le funzioni richieste nella traccia,
             * per poi richiamare i metodi specifi.
             */
            System.out.println("\nScegliere la funzione: \n-Scrivere 1 per la funzione ricerca e stampa delle coppie con a<=x<=b, e lunghezza della stringa <=s. \n-Scrivere 2 per la funzione stampa di tutte le coppie aventi valori del primo elemento x>=c.\n-Scrivere 0 per terminare l'esecuzione del programma.\n");
            int v = scanner.nextInt();
            
            if(v == 0){
                break;
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
        
        /*
         * Viene chiuso lo Scanner utilizzato per la lettura del file di input.
         */
        scanner.close();
   }
}
