package javaPython;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
    	try {
            // Créez une instance de Runtime
            Runtime runtime = Runtime.getRuntime();

            // Spécifiez la commande pour exécuter le script Python
            String command = "python last.py [0,0,1,0,1,1,2,2,0]";

            // Exécutez la commande
            Process process = runtime.exec(command);

            // Récupérez la sortie du script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Attendez que le script Python se termine
            int exitCode = process.waitFor();
            System.out.println("Le script Python a été exécuté avec le code de sortie : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
