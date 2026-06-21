import java.util.Collection;

public class PatientBST {
    private BSTNode root;

    public void insert(Patient patient) {
        root = insertRec(root, patient);
    }

    private BSTNode insertRec(BSTNode current, Patient patient) {
        if (current == null) {
            return new BSTNode(patient);
        }
        if (patient.getPatientId() < current.patient.getPatientId()) {
            current.left = insertRec(current.left, patient);
        } else if (patient.getPatientId() > current.patient.getPatientId()) {
            current.right = insertRec(current.right, patient);
        } else {
            current.patient = patient;
        }
        return current;
    }

    public Patient search(int patientId) {
        BSTNode result = searchRec(root, patientId);
        if (result == null) {
            return null;
        }
        return result.patient;
    }

    private BSTNode searchRec(BSTNode current, int patientId) {
        if (current == null || current.patient.getPatientId() == patientId) {
            return current;
        }
        if (patientId < current.patient.getPatientId()) {
            return searchRec(current.left, patientId);
        }
        return searchRec(current.right, patientId);
    }

    public void delete(int patientId) {
        root = deleteRec(root, patientId);
    }

    private BSTNode deleteRec(BSTNode current, int patientId) {
        if (current == null) {
            return null;
        }

        if (patientId < current.patient.getPatientId()) {
            current.left = deleteRec(current.left, patientId);
        } else if (patientId > current.patient.getPatientId()) {
            current.right = deleteRec(current.right, patientId);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            BSTNode smallestNode = findSmallest(current.right);
            current.patient = smallestNode.patient;
            current.right = deleteRec(current.right, smallestNode.patient.getPatientId());
        }
        return current;
    }

    private BSTNode findSmallest(BSTNode current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void rebuild(Collection<Patient> patients) {
        root = null;
        for (Patient patient : patients) {
            insert(patient);
        }
    }
}
