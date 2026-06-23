public class HistoryNode {
    EmergencyCase emergencyCase;
    HistoryNode next;

    public HistoryNode(EmergencyCase emergencyCase) {
        this.emergencyCase = emergencyCase;
        this.next = null;
    }
}
