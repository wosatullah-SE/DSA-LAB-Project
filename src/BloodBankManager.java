import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class BloodBankManager {
    private HashMap<String, BloodBank> bloodBankMap;

    public BloodBankManager() {
        bloodBankMap = new HashMap<String, BloodBank>();
    }

    public void addOrUpdateBloodBank(BloodBank bloodBank) {
        bloodBankMap.put(bloodBank.getBloodGroup().toUpperCase(), bloodBank);
    }

    public BloodBank searchBloodGroup(String bloodGroup) {
        if (bloodGroup == null) {
            return null;
        }
        return bloodBankMap.get(bloodGroup.toUpperCase());
    }

    public boolean issueBlood(String bloodGroup, int units) {
        BloodBank bank = searchBloodGroup(bloodGroup);
        if (bank == null) {
            return false;
        }
        return bank.reduceUnits(units);
    }

    public Collection<BloodBank> getAllBloodBanks() {
        return bloodBankMap.values();
    }

    public int size() {
        return bloodBankMap.size();
    }

    public String displayAllBloodBanks() {
        if (bloodBankMap.isEmpty()) {
            return "No blood bank records found.";
        }
        StringBuilder builder = new StringBuilder();
        for (BloodBank bank : bloodBankMap.values()) {
            builder.append(bank).append("\n");
        }
        return builder.toString();
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (BloodBank bank : bloodBankMap.values()) {
                writer.write(bank.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving blood banks: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    addOrUpdateBloodBank(BloodBank.fromFileLine(line));
                } catch (Exception ex) {
                    System.out.println("Skipped invalid blood bank record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading blood banks: " + e.getMessage());
        }
    }
}
