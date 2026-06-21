public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String bloodGroup;
    private String phone;

    public Patient(int patientId, String name, int age, String bloodGroup, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toFileLine() {
        return patientId + "|" + name + "|" + age + "|" + bloodGroup + "|" + phone;
    }

    public static Patient fromFileLine(String line) {
        String[] data = line.split("\\|", -1);
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        int age = Integer.parseInt(data[2]);
        String bloodGroup = data[3];
        String phone = data.length > 4 ? data[4] : "";
        return new Patient(id, name, age, bloodGroup, phone);
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                " | Name: " + name +
                " | Age: " + age +
                " | Blood Group: " + bloodGroup +
                " | Phone: " + phone;
    }
}
