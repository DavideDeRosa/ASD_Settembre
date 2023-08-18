/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 * 
 * Esecuzione:
 * Per compilare: javac Esercizio1.java
 * Per eseguire: java Esercizio1 <T>
 * La T richiesta è quella specificata nella consegna, che ci permette di impostare il numero medio di accessi desiderato.
 * 
 * Considerazioni e richieste extra:
 * Ho scelto di implementare la struttura dati richiesta attraverso l'utilizzo di una Tabella Hash con Liste di Trabocco. Avendo una 
 * distribuzione uniforme delle chiavi generate in maniera casuale possiamo suddividere la nostra tabella in intervalli uniformi.
 * La funzione di hashing ci permette di trovare la posizione nella quale inserire la chiave, puntando all'intervallo corretto.
 * Avendo una implementazione con liste di trabocco, ogni intervallo avra' una lista associata. Per garantire, a livello teorico,
 * il numero di accessi medi 'T', possiamo utilizzare la formula (1 + fattore di carico) = T. In questo modo, troviamo il fattore di
 * carico per ogni 'T' data in input. Viene poi effettuata una suddivisione in intervalli uniformi della struttura. Passando al lato
 * 'sperimentale', andiamo a calcolare il numero di accessi medi alla struttura attraverso contatori, che ci permettono di verificare
 * la correttezza della struttura, rispettando le richieste della traccia. Infine viene stampato in console l'NMA inerente all'operazione
 * di verifica effettuata sulle 300 chiavi generate in maniera casuale.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Esercizio1 {

    private static ArrayList<ArrayList<Coppia>> array; //struttura dati basata su tabella Hash con liste di trabocco

     /*
     * Creazione classe Coppia
     */
    private static class Coppia{
        private int k;
        private int np;

        public Coppia(int k, int np){
            this.k = k;
            this.np = np;
        }

        public int getK(){
            return k;
        }

        public int getNP(){
            return np;
        }

        public void setNP(int np){
            this.np = np;
        }

        public String toString(){
            return k + ", " + np;
        }
    }

    private static class HashTable{

        private int size = 0; //numero di elementi presenti nella Hash Table
        private int m = 0; //capacità della Hash Table
        private int intervallo = 0; //intervallo utilizzato per avere una suddivisione uniforme della chiavi nella tabella

        /*
         * Viene inizializzato il nostro oggetto HashTable, calcolando diversi fattori.
         */
        public HashTable(int fdc){
            fdc = fdc - 1; //il fattore di carico viene calcolato sottraendo 1 alla nostra T, tramite la formula apposita (passata come parametro)
            m = (10000 / fdc); //viene calcolata la capacita' della tabella hash
            intervallo = (1000000000 / m); //viene calcolato l'intervallo da utilizzare, in base alla T

            array = new ArrayList<>(m);

            /*
             * Viene riempita la struttura dati con 'm' ArrayList vuoti.
             */
            for(int i = 0; i < m; i++){
                array.add(i, new ArrayList<Coppia>());
            }
        }

        /*
         * Viene implementato il metodo 'inserisci(k, np)'. Una volta verificata la presenza della chiave nella struttura, viene
         * anche calcolato l'indice della lista dove deve essere inserita la nostra coppia. Se presente, il valore 'np' viene sostituito.
         * Nel caso di chiave non presente, viene inserita la nuova coppia nella lista corrispondente all'indice 'i', incrementando anche
         * il numero di coppie presenti nella struttura.
         */
        public void inserisci(int k, int np){
            int i = hash(k);
            int x = verifica(k);

            if(x == -1){
                array.get(i).add(new Coppia(k, np));
                size++;
            }else{
                array.get(i).get(x).setNP(np);
            }
        }

        /*
         * Viene implementato il metodo 'verifica(k)'. Dopo aver calcolato l'indice della lista dove dovrebbe essere presente la 
         * nostra chiave, si procede con uno scorrimento della lista per trovare la coppia richiesta. Se presente, viene restituito l'indice
         * della lista di trabocco dove è presente la chiave. Nel caso di chiave non presente, viene restituito '-1', che indica la non 
         * presenza in struttura.
         */
        public int verifica(int k){
            int i = hash(k);
            ArrayList<Coppia> a = array.get(i);

            for(int j = 0; j < a.size(); j++){
                if(a.get(j).getK() == k){
                    System.out.println(a.get(j));
                    return j;
                }
            }

            return -1;
        }

        /*
         * Viene calcolato l'indice della lista corrispondente all'intervallo della chiave. Avendo una suddivisione uniforme per le 
         * chiavi in intervalli equidistanti, insereriamo in modo semplice le chiavi nella struttura. 
         */
        public int hash(int k){
            return (k - 1) / intervallo;
        }

        /*
         * Viene restituito il numero di coppie presenti nella struttura.
         */
        public int getSize(){
            return size;
        }

    }

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input errato!");
            System.exit(1);
        }

        HashTable table = new HashTable(Integer.parseInt(args[0]));

        Random r = new Random(1054948);

        /*
         * Vengono inserite le diecimila coppie casuali all'interno della Hash Table.
         */
        for(int i = 0; i < 10000; i++){
            int k = r.nextInt(1000000000) + 1;
            int np = r.nextInt(700) + 1;

            table.inserisci(k, np);
        }

        Random r2 = new Random(7105419);
        int somma = 0; //contatore utilizzato per la stampa finale dell'NMA

        try {
			PrintWriter pw = new PrintWriter(new File("output1.txt"));

            /*
             * Viene verificata la presenza delle 300 chiavi generate casualmente.
             */
            for(int i = 0; i < 300; i++){
                int k = r2.nextInt(1000000000) + 1;

                int indice = table.verifica(k);

                /*
                 * Se 'indice == -1', non è presente la chiave nella struttura. Viene quindi calcolato il numero di accessi alla struttura
                 * sommando 1 ad il numero di elementi presenti nella lista corrispondente all'intervallo della chiave.
                 * In caso contrario, viene sommato 1 al conteggio di accessi alla lista.
                 * Come da richiesta nella consegna, viene scritto in un File di output la chiave con il numero di accessi effettuati
                 * per la verifica di quella chiave.
                 */
                if(indice == -1){
                    int accessi = array.get(table.hash(k)).size() + 1;
                    pw.println(k + ", " + accessi);
                    somma = somma + accessi;
                }else{
                    int y = table.hash(k);
                    ArrayList<Coppia> a = array.get(y);
                    int cont = 0;

                    for(int j = 0; j < a.size(); j++){
                        if(a.get(j).getK() != k){
                            cont++;
                        }else{
                            somma = somma + (cont + 1);
                            pw.println(k + ", " + (cont + 1));
                            break;
                        }
                    }
                }
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Viene stampato il valore dell'NMA calcolato in maniera sperimentale, sulle 300 chiavi casuali.
         */
        System.out.println(somma / 300);
    }

}
