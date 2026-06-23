import java.util.ArrayList;

public class MaxHeapPriorityQueue {
    private ArrayList<EmergencyCase> heap;

    public MaxHeapPriorityQueue() {
        heap = new ArrayList<EmergencyCase>();
    }

    public void insert(EmergencyCase emergencyCase) {
        heap.add(emergencyCase);
        heapifyUp(heap.size() - 1);
    }

    public EmergencyCase peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public EmergencyCase extractMax() {
        if (heap.isEmpty()) {
            return null;
        }
        EmergencyCase max = heap.get(0);
        EmergencyCase last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return max;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).comparePriority(heap.get(parent)) > 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = (2 * index) + 1;
            int right = (2 * index) + 2;
            int largest = index;

            if (left < heap.size() && heap.get(left).comparePriority(heap.get(largest)) > 0) {
                largest = left;
            }
            if (right < heap.size() && heap.get(right).comparePriority(heap.get(largest)) > 0) {
                largest = right;
            }
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int first, int second) {
        EmergencyCase temp = heap.get(first);
        heap.set(first, heap.get(second));
        heap.set(second, temp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public String displayQueue() {
        if (heap.isEmpty()) {
            return "No waiting emergency cases in priority queue.";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Max-Heap Priority Queue (highest severity appears first internally):\n");
        for (int i = 0; i < heap.size(); i++) {
            builder.append("Heap Index ").append(i).append(" -> ").append(heap.get(i)).append("\n");
        }
        return builder.toString();
    }
}
