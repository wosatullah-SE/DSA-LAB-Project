public class BloodBank {

    private String bloodGroup;
    private int unitsAvailable;

    public BloodBank(String bloodGroup,
                     int unitsAvailable) {

        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    @Override
    public String toString() {

        return "Blood Group: " + bloodGroup +
                " | Units Available: " + unitsAvailable;
    }
}