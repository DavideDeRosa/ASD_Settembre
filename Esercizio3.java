/*
 *  Nome: Davide
 *  Cognome: De Rosa
 *  Matricola: 1054948
 *  Email: davide.derosa@studio.unibo.it
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Esercizio3 {
    
    public static int contaPromossi(double[] I, double k, int start, int end){
        if(start > end){
            return 0;
        }else{
            int mid = start + ((end - start) / 2);

            if (I[mid] <= k) {
                return contaPromossi(I, k, mid + 1, end);
            } else{
                return (end - mid + 1) + contaPromossi(I, k, start, mid - 1);
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try {
            Scanner s = new Scanner(new File("es3.txt"));

            int n = 0;
            double[] I = null;
            double k = 0;

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
            
            System.out.println("Dipendenti premiati: " + contaPromossi(I, k, 0, I.length - 1));

            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato!");
            System.exit(0);
        }
    }
}
