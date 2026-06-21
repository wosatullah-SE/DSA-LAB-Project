import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class HospitalManager {
    private HashMap<Integer, Hospital> hospitalMap;
    private Hospital[] hospitalArray;
    private int hospitalCount;

    public HospitalManager() {
        hospitalMap = new HashMap<Integer, Hospital>();
        hospitalArray = new Hospital[100];
        hospitalCount = 0;
    }

    public boolean addHospital(Hospital hospital) {
        if (hospitalMap.containsKey(hospital.getHospitalId())) {
            return false;
        }
        if (hospitalCount >= hospitalArray.length) {
            return false;
        }
        hospitalMap.put(hospital.getHospitalId(), hospital);
        hospitalArray[hospitalCount] = hospital;
        hospitalCount++;
        return true;
    }

    public boolean updateHospital(Hospital hospital) {
        if (!hospitalMap.containsKey(hospital.getHospitalId())) {
            return false;
        }
        hospitalMap.put(hospital.getHospitalId(), hospital);
        rebuildArray();
        return true;
    }

    public Hospital searchHospital(int hospitalId) {
        return hospitalMap.get(hospitalId);
    }

    public Hospital findHospitalWithBeds() {
        Hospital best = null;
        for (int i = 0; i < hospitalCount; i++) {
            Hospital hospital = hospitalArray[i];
            if (hospital.getAvailableBeds() > 0) {
                if (best == null || hospital.getAvailableBeds() > best.getAvailableBeds()) {
                    best = hospital;
                }
            }
        }
        return best;
    }

    public Collection<Hospital> getAllHospitals() {
        return hospitalMap.values();
    }

    public Hospital[] getHospitalArray() {
        Hospital[] copy = new Hospital[hospitalCount];
        for (int i = 0; i < hospitalCount; i++) {
            copy[i] = hospitalArray[i];
        }
        return copy;
    }

    public int size() {
        return hospitalMap.size();
    }

    public String displayAllHospitals() {
        if (hospitalCount == 0) {
            return "No hospital records found.";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hospitalCount; i++) {
            builder.append(hospitalArray[i]).append("\n");
        }
        return builder.toString();
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < hospitalCount; i++) {
                writer.write(hospitalArray[i].toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving hospitals: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
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
                    addHospital(Hospital.fromFileLine(line));
                } catch (Exception ex) {
                    System.out.println("Skipped invalid hospital record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading hospitals: " + e.getMessage());
        }
    }

    private void rebuildArray() {
        hospitalArray = new Hospital[100];
        hospitalCount = 0;
        for (Hospital hospital : hospitalMap.values()) {
            hospitalArray[hospitalCount] = hospital;
            hospitalCount++;
        }
    }
}
