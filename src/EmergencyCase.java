public class EmergencyCase {
    private int caseId;
    private int patientId;
    private String patientName;
    private String location;
    private String condition;
    private int severity;
    private String requiredBloodGroup;
    private String status;
    private int assignedAmbulanceId;
    private String assignedHospitalName;
    private long createdAt;

    public EmergencyCase(int caseId, int patientId, String patientName, String location,
                         String condition, int severity, String requiredBloodGroup) {
        this(caseId, patientId, patientName, location, condition, severity, requiredBloodGroup,
                "WAITING", 0, "Not Assigned", System.currentTimeMillis());
    }

    public EmergencyCase(int caseId, int patientId, String patientName, String location,
                         String condition, int severity, String requiredBloodGroup,
                         String status, int assignedAmbulanceId, String assignedHospitalName, long createdAt) {
        this.caseId = caseId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.location = location;
        this.condition = condition;
        this.severity = severity;
        this.requiredBloodGroup = requiredBloodGroup;
        this.status = status;
        this.assignedAmbulanceId = assignedAmbulanceId;
        this.assignedHospitalName = assignedHospitalName;
        this.createdAt = createdAt;
    }

    public int getCaseId() {
        return caseId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getLocation() {
        return location;
    }

    public String getCondition() {
        return condition;
    }

    public int getSeverity() {
        return severity;
    }

    public String getRequiredBloodGroup() {
        return requiredBloodGroup;
    }

    public String getStatus() {
        return status;
    }

    public int getAssignedAmbulanceId() {
        return assignedAmbulanceId;
    }

    public String getAssignedHospitalName() {
        return assignedHospitalName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isWaiting() {
        return "WAITING".equalsIgnoreCase(status);
    }

    public void assign(Ambulance ambulance, Hospital hospital) {
        assignedAmbulanceId = ambulance.getAmbulanceId();
        assignedHospitalName = hospital.getName();
        status = "DISPATCHED";
    }

    public void complete() {
        status = "COMPLETED";
    }

    public int comparePriority(EmergencyCase other) {
        if (this.severity != other.severity) {
            return this.severity - other.severity;
        }
        if (this.createdAt < other.createdAt) {
            return 1;
        }
        if (this.createdAt > other.createdAt) {
            return -1;
        }
        return 0;
    }

    public String toFileLine() {
        return caseId + "|" + patientId + "|" + patientName + "|" + location + "|" +
                condition + "|" + severity + "|" + requiredBloodGroup + "|" + status + "|" +
                assignedAmbulanceId + "|" + assignedHospitalName + "|" + createdAt;
    }

    public static EmergencyCase fromFileLine(String line) {
        String[] data = line.split("\\|", -1);
        int caseId = Integer.parseInt(data[0]);
        int patientId = Integer.parseInt(data[1]);
        String patientName = data[2];
        String location = data[3];
        String condition = data[4];
        int severity = Integer.parseInt(data[5]);
        String bloodGroup = data[6];
        String status = data[7];
        int ambulanceId = Integer.parseInt(data[8]);
        String hospital = data[9];
        long time = Long.parseLong(data[10]);
        return new EmergencyCase(caseId, patientId, patientName, location, condition, severity,
                bloodGroup, status, ambulanceId, hospital, time);
    }

    @Override
    public String toString() {
        return "Case ID: " + caseId +
                " | Patient: " + patientName + " (" + patientId + ")" +
                " | Location: " + location +
                " | Severity: " + severity + "/10" +
                " | Blood: " + requiredBloodGroup +
                " | Status: " + status +
                " | Ambulance: " + (assignedAmbulanceId == 0 ? "Not Assigned" : assignedAmbulanceId) +
                " | Hospital: " + assignedHospitalName +
                " | Condition: " + condition;
    }
}
