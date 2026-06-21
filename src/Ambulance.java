public class Ambulance {
    private int ambulanceId;
    private String driverName;
    private String currentLocation;
    private boolean available;
    private String phone;

    public Ambulance(int ambulanceId, String driverName, String currentLocation, boolean available, String phone) {
        this.ambulanceId = ambulanceId;
        this.driverName = driverName;
        this.currentLocation = currentLocation;
        this.available = available;
        this.phone = phone;
    }

    public int getAmbulanceId() {
        return ambulanceId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getPhone() {
        return phone;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toFileLine() {
        return ambulanceId + "|" + driverName + "|" + currentLocation + "|" + available + "|" + phone;
    }

    public static Ambulance fromFileLine(String line) {
        String[] data = line.split("\\|", -1);
        int id = Integer.parseInt(data[0]);
        String driver = data[1];
        String location = data[2];
        boolean available = Boolean.parseBoolean(data[3]);
        String phone = data.length > 4 ? data[4] : "";
        return new Ambulance(id, driver, location, available, phone);
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Busy";
        return "Ambulance ID: " + ambulanceId +
                " | Driver: " + driverName +
                " | Location: " + currentLocation +
                " | Status: " + status +
                " | Phone: " + phone;
    }
}
