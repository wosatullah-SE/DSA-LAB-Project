public class Patient {

    private int patientId;
    private String name;
    private int age;
    private String bloodGroup;

    public Patient(int patientId,
                   String name,
                   int age,
                   String bloodGroup) {

        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
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

    @Override
    public String toString() {

        return "ID: " + patientId +
                " | Name: " + name +
                " | Age: " + age +
                " | Blood Group: " + bloodGroup;
    }
}