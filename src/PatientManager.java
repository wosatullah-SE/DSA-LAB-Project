import java.util.Collection;
import java.util.HashMap;

public class PatientManager {
    private HashMap<Integer, Patient> patients;
    private PatientBST patientBST;

    public PatientManager() {
        patients = new HashMap<Integer, Patient>();
        patientBST = new PatientBST();
    }

    public boolean addPatient(Patient patient) {
        if (patients.containsKey(patient.getPatientId())) {
            return false;
        }
        patients.put(patient.getPatientId(), patient);
        patientBST.insert(patient);
        return true;
    }

    public boolean updatePatient(Patient patient) {
        if (!patients.containsKey(patient.getPatientId())) {
            return false;
        }
        patients.put(patient.getPatientId(), patient);
        patientBST.rebuild(patients.values());
        return true;
    }

    public Patient searchPatient(int id) {
        return patients.get(id);
    }

    public Patient searchPatientUsingBST(int id) {
        return patientBST.search(id);
    }

    public boolean deletePatient(int id) {
        Patient removed = patients.remove(id);
        if (removed == null) {
            return false;
        }
        patientBST.delete(id);
        return true;
    }

    public Collection<Patient> getAllPatients() {
        return patients.values();
    }

    public int size() {
        return patients.size();
    }

    public String displayAllPatients() {
        if (patients.isEmpty()) {
            return "No patient records found.";
        }
        StringBuilder builder = new StringBuilder();
        for (Patient patient : patients.values()) {
            builder.append(patient).append("\n");
        }
        return builder.toString();
    }
}
