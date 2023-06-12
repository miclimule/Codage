package javaPython;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            // Chemin vers le fichier Python
            String pythonScriptPath = "script.py";
            // Données d'entrée
            String inputData = "Données à traiter";

            // Construction de la commande d'exécution du script Python
            String[] cmd = {"python", pythonScriptPath};

            // Exécution du script Python
            Process process = Runtime.getRuntime().exec(cmd);

            // Envoi des données d'entrée au script Python
            process.getOutputStream().write(inputData.getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().close();

            // Récupération de la sortie du script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // Attente de la fin de l'exécution du script Python
            int exitCode = process.waitFor();

            // Affichage des résultats
            System.out.println("Sortie du script Python : " + output.toString());
            System.out.println("Code de sortie : " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
