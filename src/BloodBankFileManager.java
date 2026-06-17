import java.io.*;

public class BloodBankFileManager {

    private static final String FILE_NAME =
            "bloodbanks.txt";

    public static void saveBloodRecords(
            BloodBankManager manager) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME));

            for(BloodBank bloodBank :
                    manager.getBloodRecords()) {

                writer.write(
                        bloodBank.getBloodGroup()
                                + "," +
                                bloodBank.getUnitsAvailable()
                );

                writer.newLine();
            }

            writer.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Saving Blood Records"
            );
        }
    }

    public static void loadBloodRecords(
            BloodBankManager manager) {

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

                String bloodGroup =
                        data[0];

                int units =
                        Integer.parseInt(data[1]);

                BloodBank bloodBank =
                        new BloodBank(
                                bloodGroup,
                                units
                        );

                manager.addBloodRecord(
                        bloodBank
                );
            }

            reader.close();

        } catch(Exception e) {

            System.out.println(
                    "Error Loading Blood Records"
            );
        }
    }
}