import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RcloneSync {
    public static void main(String[] args) {
        try {
            // Meny for brukerinput
            System.out.println("Velg en synkroniseringsoperasjon:");
            System.out.println("1) Synkroniser fra OneDrive til lokal mappe");
            System.out.println("2) Synkroniser fra lokal mappe til OneDrive");
            System.out.print("Skriv inn valget ditt (1 eller 2): ");

            // Les valg fra brukeren
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String choice = reader.readLine();

            // Utfør basert på valg
            if ("1".equals(choice)) {
                System.out.println("Starter synkronisering fra OneDrive til lokal mappe...");
                runCommand("rclone sync onedrive-skole-sin:/ ~/local-folder --progress");
            } else if ("2".equals(choice)) {
                System.out.println("Starter synkronisering fra lokal mappe til OneDrive...");
                runCommand("rclone sync ~/local-folder onedrive-skole-sin:/ --progress");
            } else {
                System.out.println("Ugyldig valg. Avslutter.");
            }
        } catch (IOException e) {
            System.err.println("En feil oppstod: " + e.getMessage());
        }
    }

    // Metode for å kjøre terminalkommandoer
    private static void runCommand(String command) {
        try {
            // Start en ny prosess med ønsket kommando
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Les utdata fra prosessen
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Vent på at prosessen skal fullføres
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Kommando fullført.");
            } else {
                System.err.println("Kommando mislyktes med exit-kode: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Feil ved kjøring av kommando: " + e.getMessage());
        }
    }
}
