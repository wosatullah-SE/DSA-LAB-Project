public class Hospital {

    private int hospitalId;
    private String hospitalName;
    private String location;
    private int availableBeds;

    public Hospital(int hospitalId,
                    String hospitalName,
                    String location,
                    int availableBeds) {

        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.location = location;
        this.availableBeds = availableBeds;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getLocation() {
        return location;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    @Override
    public String toString() {

        return "Hospital ID: " + hospitalId +
                " | Name: " + hospitalName +
                " | Location: " + location +
                " | Beds: " + availableBeds;
    }
}