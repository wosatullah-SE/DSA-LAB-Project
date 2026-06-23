import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppData {
    public static final String PATIENT_FILE = "patients.txt";
    public static final String HOSPITAL_FILE = "hospitals.txt";
    public static final String BLOOD_BANK_FILE = "bloodbanks.txt";
    public static final String AMBULANCE_FILE = "ambulances.txt";
    public static final String EMERGENCY_FILE = "emergencies.txt";

    public static PatientManager patientManager = new PatientManager();
    public static HospitalManager hospitalManager = new HospitalManager();
    public static BloodBankManager bloodBankManager = new BloodBankManager();
    public static CircularAmbulanceQueue ambulanceQueue = new CircularAmbulanceQueue(50);
    public static MaxHeapPriorityQueue emergencyQueue = new MaxHeapPriorityQueue();
    public static EmergencyHistoryLinkedList emergencyHistory = new EmergencyHistoryLinkedList();
    public static RouteGraph routeGraph = new RouteGraph();

    private static boolean loaded = false;

    public static void loadAll() {
        if (loaded) {
            return;
        }
        patientManager = new PatientManager();
        hospitalManager = new HospitalManager();
        bloodBankManager = new BloodBankManager();
        ambulanceQueue = new CircularAmbulanceQueue(50);
        emergencyQueue = new MaxHeapPriorityQueue();
        emergencyHistory = new EmergencyHistoryLinkedList();
        routeGraph = new RouteGraph();

        routeGraph.loadDefaultCityMap();
        PatientFileManager.loadPatients(patientManager, PATIENT_FILE);
        hospitalManager.loadFromFile(HOSPITAL_FILE);
        bloodBankManager.loadFromFile(BLOOD_BANK_FILE);
        ambulanceQueue.loadFromFile(AMBULANCE_FILE);
        loadEmergencyCases();

        loadDefaultDataIfFilesAreEmpty();
        loaded = true;
    }

    public static void saveAll() {
        PatientFileManager.savePatients(patientManager, PATIENT_FILE);
        hospitalManager.saveToFile(HOSPITAL_FILE);
        bloodBankManager.saveToFile(BLOOD_BANK_FILE);
        ambulanceQueue.saveToFile(AMBULANCE_FILE);
        saveEmergencyCases();
    }

    private static void loadEmergencyCases() {
        File file = new File(EMERGENCY_FILE);
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
                    EmergencyCase emergencyCase = EmergencyCase.fromFileLine(line);
                    emergencyHistory.addCase(emergencyCase);
                    if (emergencyCase.isWaiting()) {
                        emergencyQueue.insert(emergencyCase);
                    }
                } catch (Exception ex) {
                    System.out.println("Skipped invalid emergency record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading emergency records: " + e.getMessage());
        }
    }

    public static void saveEmergencyCases() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMERGENCY_FILE))) {
            EmergencyCase[] cases = emergencyHistory.toArray();
            for (int i = 0; i < cases.length; i++) {
                writer.write(cases[i].toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving emergency records: " + e.getMessage());
        }
    }

    private static void loadDefaultDataIfFilesAreEmpty() {
        if (patientManager.size() == 0) {
            patientManager.addPatient(new Patient(1, "Ali Raza", 22, "O+", "03001234567"));
            patientManager.addPatient(new Patient(2, "Sara Khan", 30, "A+", "03019876543"));
            patientManager.addPatient(new Patient(3, "Ahmed Bilal", 41, "B-", "03111222333"));
        }

        if (hospitalManager.size() == 0) {
            hospitalManager.addHospital(new Hospital(101, "City Hospital", "City Hospital", 7, "042111222333"));
            hospitalManager.addHospital(new Hospital(102, "General Hospital", "General Hospital", 4, "042444555666"));
            hospitalManager.addHospital(new Hospital(103, "Life Care Clinic", "Life Care Clinic", 3, "042777888999"));
        }

        if (bloodBankManager.size() == 0) {
            bloodBankManager.addOrUpdateBloodBank(new BloodBank("O+", "Swift Blood Center", "Central Blood Bank", 15, "03210000001"));
            bloodBankManager.addOrUpdateBloodBank(new BloodBank("A+", "Hope Blood Bank", "Central Blood Bank", 10, "03210000002"));
            bloodBankManager.addOrUpdateBloodBank(new BloodBank("B-", "Red Life Bank", "North Blood Bank", 6, "03210000003"));
            bloodBankManager.addOrUpdateBloodBank(new BloodBank("AB+", "Care Blood Bank", "North Blood Bank", 8, "03210000004"));
        }

        if (ambulanceQueue.size() == 0) {
            ambulanceQueue.addAmbulance(new Ambulance(201, "Usman Driver", "Ambulance Base", true, "03005550111"));
            ambulanceQueue.addAmbulance(new Ambulance(202, "Hamza Driver", "Main Road", true, "03005550222"));
            ambulanceQueue.addAmbulance(new Ambulance(203, "Bilal Driver", "Ring Road", true, "03005550333"));
        }

        saveAll();
    }
}
