import java.util.ArrayList;

public class BloodBankManager {

    private ArrayList<BloodBank> bloodRecords;

    public BloodBankManager() {

        bloodRecords = new ArrayList<>();
    }

    public void addBloodRecord(BloodBank bloodBank) {

        bloodRecords.add(bloodBank);
    }

    public ArrayList<BloodBank> getBloodRecords() {

        return bloodRecords;
    }

    public BloodBank searchBloodGroup(String group) {

        for(BloodBank bloodBank : bloodRecords) {

            if(bloodBank.getBloodGroup()
                    .equalsIgnoreCase(group)) {

                return bloodBank;
            }
        }

        return null;
    }
}