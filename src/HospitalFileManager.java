import java.io.*;

public class HospitalFileManager {

    private static final String FILE_NAME =
            "hospitals.txt";

    public static void saveHospitals(
            HospitalManager manager) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME));

            for(Hospital hospital :
                    manager.getHospitals()) {

                writer.write(
                        hospital.getHospitalId() + "," +
                                hospital.getHospitalName() + "," +
                                hospital.getLocation() + "," +
                                hospital.getAvailableBeds()
                );

                writer.newLine();
            }

            writer.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Saving Hospitals"
            );
        }
    }

    public static void loadHospitals(
            HospitalManager manager) {

        File file =
                new File(FILE_NAME);

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

                String location =
                        data[2];

                int beds =
                        Integer.parseInt(data[3]);

                Hospital hospital =
                        new Hospital(
                                id,
                                name,
                                location,
                                beds
                        );

                manager.addHospital(hospital);
            }

            reader.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Loading Hospitals"
            );
        }
    }
}