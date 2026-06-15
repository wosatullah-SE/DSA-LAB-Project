import java.io.*;

public class PatientFileManager {

    private static final String FILE_NAME =
            "patients.txt";

    public static void savePatients(
            PatientManager manager) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME));

            for(Patient patient :
                    manager.getPatients().values()) {

                writer.write(
                        patient.getPatientId() + "," +
                                patient.getName() + "," +
                                patient.getAge() + "," +
                                patient.getBloodGroup()
                );

                writer.newLine();
            }

            writer.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Saving File"
            );
        }
    }

    public static void loadPatients(
            PatientManager manager) {

        File file = new File(FILE_NAME);

        if(!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME));

            String line;

            while((line = reader.readLine()) != null) {

                String[] data =
                        line.split(",");

                int id =
                        Integer.parseInt(data[0]);

                String name =
                        data[1];

                int age =
                        Integer.parseInt(data[2]);

                String bloodGroup =
                        data[3];

                Patient patient =
                        new Patient(
                                id,
                                name,
                                age,
                                bloodGroup
                        );

                manager.addPatient(patient);
            }

            reader.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Loading File"
            );
        }
    }
}