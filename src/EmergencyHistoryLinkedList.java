public class EmergencyHistoryLinkedList {
    private HistoryNode head;
    private HistoryNode tail;
    private int size;

    public void addCase(EmergencyCase emergencyCase) {
        HistoryNode newNode = new HistoryNode(emergencyCase);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public EmergencyCase searchByCaseId(int caseId) {
        HistoryNode current = head;
        while (current != null) {
            if (current.emergencyCase.getCaseId() == caseId) {
                return current.emergencyCase;
            }
            current = current.next;
        }
        return null;
    }

    public EmergencyCase[] toArray() {
        EmergencyCase[] result = new EmergencyCase[size];
        HistoryNode current = head;
        int index = 0;
        while (current != null) {
            result[index] = current.emergencyCase;
            index++;
            current = current.next;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public String displayHistory() {
        if (head == null) {
            return "Emergency history is empty.";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Emergency Case History using Linked List:\n");
        HistoryNode current = head;
        while (current != null) {
            builder.append(current.emergencyCase).append("\n");
            current = current.next;
        }
        return builder.toString();
    }
}
