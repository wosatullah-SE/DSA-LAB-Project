public class Hospital {
    private int hospitalId;
    private String name;
    private String location;
    private int availableBeds;
    private String phone;

    public Hospital(int hospitalId, String name, String location, int availableBeds, String phone) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.location = location;
        this.availableBeds = availableBeds;
        this.phone = phone;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void reduceOneBed() {
        if (availableBeds > 0) {
            availableBeds--;
        }
    }

    public String toFileLine() {
        return hospitalId + "|" + name + "|" + location + "|" + availableBeds + "|" + phone;
    }

    public static Hospital fromFileLine(String line) {
        String[] data = line.split("\\|", -1);
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String location = data[2];
        int beds = Integer.parseInt(data[3]);
        String phone = data.length > 4 ? data[4] : "";
        return new Hospital(id, name, location, beds, phone);
    }

    @Override
    public String toString() {
        return "Hospital ID: " + hospitalId +
                " | Name: " + name +
                " | Location: " + location +
                " | Beds: " + availableBeds +
                " | Phone: " + phone;
    }
}
