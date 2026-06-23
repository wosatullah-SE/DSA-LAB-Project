import javax.swing.*;
import java.awt.*;

public class RoutePanel extends JPanel {
    private JComboBox<String> sourceBox;
    private JComboBox<String> destinationBox;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextArea displayArea;

    public RoutePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Route Management - Graph + Dijkstra + BFS + Arrays for Path");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        String[] locations = AppData.routeGraph.getLocationsArray();
        sourceBox = new JComboBox<String>(locations);
        destinationBox = new JComboBox<String>(locations);
        sourceField = new JTextField();
        destinationField = new JTextField();
        inputPanel.add(new JLabel("Source from List"));
        inputPanel.add(sourceBox);
        inputPanel.add(new JLabel("Destination from List"));
        inputPanel.add(destinationBox);
        inputPanel.add(new JLabel("Or Type Source"));
        inputPanel.add(sourceField);
        inputPanel.add(new JLabel("Or Type Destination"));
        inputPanel.add(destinationField);
        add(inputPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton dijkstraButton = new JButton("Find Shortest Route Dijkstra");
        JButton bfsButton = new JButton("Find BFS Route");
        JButton graphButton = new JButton("Display Graph");
        JButton refreshButton = new JButton("Refresh Locations");

        dijkstraButton.addActionListener(e -> findDijkstra());
        bfsButton.addActionListener(e -> findBFS());
        graphButton.addActionListener(e -> displayArea.setText(AppData.routeGraph.displayGraph()));
        refreshButton.addActionListener(e -> refreshLocationBoxes());

        buttonPanel.add(dijkstraButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(graphButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void findDijkstra() {
        String source = getSource();
        String destination = getDestination();
        RouteGraph.RouteResult result = AppData.routeGraph.findShortestPathDijkstra(source, destination);
        displayArea.setText(result.getMessage() + "\nPath Array: " + result.pathToString() +
                "\nTotal Distance: " + result.getDistance() + " km");
    }

    private void findBFS() {
        String source = getSource();
        String destination = getDestination();
        RouteGraph.RouteResult result = AppData.routeGraph.findPathBFS(source, destination);
        displayArea.setText(result.getMessage() + "\nPath Array: " + result.pathToString() +
                "\nNumber of Roads: " + result.getDistance());
    }

    private String getSource() {
        if (!sourceField.getText().trim().isEmpty()) {
            return sourceField.getText().trim();
        }
        return String.valueOf(sourceBox.getSelectedItem());
    }

    private String getDestination() {
        if (!destinationField.getText().trim().isEmpty()) {
            return destinationField.getText().trim();
        }
        return String.valueOf(destinationBox.getSelectedItem());
    }

    private void refreshLocationBoxes() {
        String[] locations = AppData.routeGraph.getLocationsArray();
        sourceBox.removeAllItems();
        destinationBox.removeAllItems();
        for (int i = 0; i < locations.length; i++) {
            sourceBox.addItem(locations[i]);
            destinationBox.addItem(locations[i]);
        }
        displayArea.setText("Locations refreshed from graph.");
    }
}
