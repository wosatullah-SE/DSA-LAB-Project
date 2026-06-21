import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PatientFileManager {
    public static void savePatients(PatientManager manager, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Patient patient : manager.getAllPatients()) {
                writer.write(patient.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving patient file: " + e.getMessage());
        }
    }

    public static void loadPatients(PatientManager manager, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    manager.addPatient(Patient.fromFileLine(line));
                } catch (Exception ex) {
                    System.out.println("Skipped invalid patient record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patient file: " + e.getMessage());
        }
    }
}
