/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 * 
 * Esecuzione:
 * Per compilare: javac Esercizio3.java
 * Per eseguire: java Esercizio3 <file input>
 * 
 * File di Input:
 * Viene utilizzato un File di input come specificato nella consegna, con la presenza dei commenti (ad esempio: # vettore I).
 * 
 * Considerazioni e richieste extra:
 * Ho scelto di implementare il tutto attraverso l'utilizzo di un algoritmo basato sul Binary Search con la tecnica del Divide-et-Impera,
 * come richiesto dalla traccia. La tecnica del Divide-et-Impera si basa su tre passaggi progressivi:
 *      -DIVIDE: si divide il problema in sottoproblemi.
 *      -IMPERA: si risolvono i sottoproblemi in maniera ricorsiva.
 *      -COMBINA: si uniscono le soluzioni dei sottoproblemi per costruire la soluzione del problema originario.
 * 
 * Nel nostro caso specifico la suddivisione in sottoproblemi riguarda la suddivisione dell'Array in un Array più piccolo, sul quale
 * richiamiamo la funzione ricorsiva. Una volta risolti tutti i sottoproblemi, andiamo a sommare il numero dei dipendenti promossi
 * ottenuti tramite le diverse chiamate ricorsive.
 * 
 * Il costo computazionale del metodo "contaPromossi()" va calcolato attraverso l'utilizzo del Master Theorem.
 * L'equazione di ricorrenza di questo metodo è T(n) = T(n/2) + O(1), che in base al Master Theorem ha soluzione T(n) = O(log n).
 * 
 * Un altra possibile implementazione, quella iterativa, non avrebbe soddisfatto la richiesta della traccia di utilizzare la 
 * tecnica del Divide-et-Impera, pertanto è stata scartata a priori.
 */

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Esercizio3 {
    
    /*
     * Viene implementato un algoritmo basato sul Binary Search di un Array con la tecnica Divide-et-Impera, come richiesto dalla traccia.
     * Vengono passati come parametri l'array ordinato I, la soglia k, e i due indici sui quali si basa il Binary Search.
     */
    public static int contaPromossi(double[] I, double k, int start, int end){
        /*
         * Questa condizione permette di concludere la ricorsione.
         */
        if(start > end){
            return 0;
        }else{
            /*
             * Si noti come l'indice medio viene generato come "mid = start + ((end - start) / 2)". 
             * Questo perchè in caso di un Array molto grande, con calcolo dell'indice medio "mid = (start + end) / 2",
             * un overflow potrebbe avvenire per Array contenenti piu di 2^30 elementi, con l'indice medio che supererebbe 
             * il massimo numero intero positivo.
             */
            int mid = start + ((end - start) / 2);

            /*
             * Viene controllato se il valore dell'Array nell'indice medio è minore o uguale alla soglia k.
             * 
             * In caso positivo, vuol dire che l'elemento centrale è sotto la soglia. Viene quindi richiamata la 
             * funzione in maniera ricorsiva sulla "parte destra" dell'Array, ossia il sottovettore I[mid+1...end].
             * 
             * In caso negativo, vuol dire che l'elemento centrale è sopra la soglia. Essendo l'Array ordinato, tutti
             * gli elementi "(end - mid + 1)" nel vettore I[mid...end] risultano sopra la soglia. Il numero di questi
             * elementi, sommati al conteggio di quelli sopra soglia in I[start...mid-1], calcolati ricorsivamente,
             * ci daranno il nostro risultato.
             * 
             * Alla fine della ricorsione, arrivati alla condizione dell'If iniziale, vengono sommate tutte le ricorsioni, ritornando
             * il numero dei dipendenti promossi.
             */
            if (I[mid] <= k) {
                return contaPromossi(I, k, mid + 1, end);
            } else{
                return (end - mid + 1) + contaPromossi(I, k, start, mid - 1);
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(1);
        }
        
        try {
            /*
             * Vengono dichiarate le variabili e gli oggetti necessarie all'implementazione.
             */
            Scanner s = new Scanner(new File(args[0]));

            int n = 0;
            double[] I = null;
            double k = 0;

            /*
             * Viene effettuata la lettura da file del numero di dipendenti, del vettore ordinato I e della soglia k.
             */
            while (s.hasNextLine()){
                String line = s.nextLine().trim();

                if(line.isEmpty()){
                    continue;
                }

                if(line.startsWith("# numero di dipendenti")){
                    n = Integer.parseInt(s.nextLine().trim());
                    I = new double[n];
                } else if(line.startsWith("# vettore I")){
                    for (int i = 0; i < n; i++) {
                        I[i] = Double.parseDouble(s.nextLine().trim());
                    }
                } else if(line.startsWith("# soglia k")){
                    k = Double.parseDouble(s.nextLine().trim());
                }
            }
            
            /*
             * Viene effettuata la stampa dei dipendenti premiati, richiamando il metodo contaPromossi().
             */
            System.out.println("Dipendenti premiati: " + contaPromossi(I, k, 0, I.length - 1));

            /*
             * Viene chiuso lo Scanner utilizzato per la lettura del file di input.
             */
            s.close();
        } catch (Exception e) {
            System.out.println("Caricamento del file non andato a buon fine!");
            System.exit(0);
        }
    }
}
