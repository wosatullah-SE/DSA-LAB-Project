import java.util.HashMap;

public class PatientManager {

    private HashMap<Integer, Patient> patients;

    public PatientManager() {

        patients = new HashMap<>();
    }

    public void addPatient(Patient patient) {

        patients.put(
                patient.getPatientId(),
                patient
        );
    }

    public Patient searchPatient(int id) {

        return patients.get(id);
    }

    public void deletePatient(int id) {

        patients.remove(id);
    }

    public HashMap<Integer, Patient> getPatients() {

        return patients;
    }
}