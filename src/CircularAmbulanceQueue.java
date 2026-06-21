import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CircularAmbulanceQueue {
    private Ambulance[] queue;
    private int front;
    private int rear;
    private int count;

    public CircularAmbulanceQueue(int capacity) {
        queue = new Ambulance[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    public boolean addAmbulance(Ambulance ambulance) {
        if (isFull() || searchAmbulance(ambulance.getAmbulanceId()) != null) {
            return false;
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = ambulance;
        count++;
        return true;
    }

    public Ambulance getNextAvailableAmbulance() {
        if (isEmpty()) {
            return null;
        }

        for (int i = 0; i < count; i++) {
            Ambulance current = removeFront();
            addAtRear(current);
            if (current.isAvailable()) {
                return current;
            }
        }
        return null;
    }

    private Ambulance removeFront() {
        Ambulance ambulance = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        return ambulance;
    }

    private void addAtRear(Ambulance ambulance) {
        rear = (rear + 1) % queue.length;
        queue[rear] = ambulance;
        count++;
    }

    public Ambulance searchAmbulance(int ambulanceId) {
        for (int i = 0; i < count; i++) {
            int index = (front + i) % queue.length;
            if (queue[index].getAmbulanceId() == ambulanceId) {
                return queue[index];
            }
        }
        return null;
    }

    public boolean markAvailable(int ambulanceId, boolean available) {
        Ambulance ambulance = searchAmbulance(ambulanceId);
        if (ambulance == null) {
            return false;
        }
        ambulance.setAvailable(available);
        return true;
    }

    public boolean isFull() {
        return count == queue.length;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public Ambulance[] toArray() {
        Ambulance[] result = new Ambulance[count];
        for (int i = 0; i < count; i++) {
            int index = (front + i) % queue.length;
            result[i] = queue[index];
        }
        return result;
    }

    public String displayAmbulances() {
        if (isEmpty()) {
            return "No ambulance records found.";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Circular Queue Front -> Rear\n");
        for (int i = 0; i < count; i++) {
            int index = (front + i) % queue.length;
            builder.append(queue[index]).append("\n");
        }
        return builder.toString();
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Ambulance[] ambulances = toArray();
            for (int i = 0; i < ambulances.length; i++) {
                writer.write(ambulances[i].toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving ambulances: " + e.getMessage());
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
                    addAmbulance(Ambulance.fromFileLine(line));
                } catch (Exception ex) {
                    System.out.println("Skipped invalid ambulance record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading ambulances: " + e.getMessage());
        }
    }
}
