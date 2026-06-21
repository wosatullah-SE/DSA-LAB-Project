public class BloodBank {
    private String bloodGroup;
    private String bankName;
    private String location;
    private int unitsAvailable;
    private String phone;

    public BloodBank(String bloodGroup, String bankName, String location, int unitsAvailable, String phone) {
        this.bloodGroup = bloodGroup.toUpperCase();
        this.bankName = bankName;
        this.location = location;
        this.unitsAvailable = unitsAvailable;
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getBankName() {
        return bankName;
    }

    public String getLocation() {
        return location;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public String getPhone() {
        return phone;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean reduceUnits(int units) {
        if (unitsAvailable < units) {
            return false;
        }
        unitsAvailable = unitsAvailable - units;
        return true;
    }

    public String toFileLine() {
        return bloodGroup + "|" + bankName + "|" + location + "|" + unitsAvailable + "|" + phone;
    }

    public static BloodBank fromFileLine(String line) {
        String[] data = line.split("\\|", -1);
        String group = data[0];
        String name = data[1];
        String location = data[2];
        int units = Integer.parseInt(data[3]);
        String phone = data.length > 4 ? data[4] : "";
        return new BloodBank(group, name, location, units, phone);
    }

    @Override
    public String toString() {
        return "Blood Group: " + bloodGroup +
                " | Bank: " + bankName +
                " | Location: " + location +
                " | Units: " + unitsAvailable +
                " | Phone: " + phone;
    }
}
