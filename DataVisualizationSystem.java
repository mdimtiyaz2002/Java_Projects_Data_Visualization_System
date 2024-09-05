import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class DataVisualizationSystem {

    private JTextField itemField;
    private JTextField amountField;
    private JButton addDataButton;
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JPanel chartPanel;
    private JButton pieChartButton;
    private JButton resetButton;
    private DefaultPieDataset pieDataset;
    private JFreeChart pieChart;
    private DefaultTableModel tableModel;
    private JTable dataTable;
    private JFrame frame;

    public DataVisualizationSystem() {
        frame = new JFrame("Data Visualization System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);

        // Create and initialize text fields
        itemField = new JTextField(10);  // Set appropriate width
        amountField = new JTextField(10);

        tablePanel = new JPanel();
        tableModel = new DefaultTableModel(new String[]{"Item", "Amount"}, 0);
        dataTable = new JTable(tableModel);
        tablePanel.add(new JScrollPane(dataTable));

        chartPanel = new JPanel();

        addDataButton = new JButton("Add Data");
        addDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        pieChartButton = new JButton("Show Pie Chart");
        pieChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPieChart();
            }
        });

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addDataButton);
        buttonPanel.add(pieChartButton);
        buttonPanel.add(resetButton);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(chartPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Add text fields to a panel (optional)
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Item:"));
        inputPanel.add(itemField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        mainPanel.add(inputPanel, BorderLayout.WEST); // Or another suitable location

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addData() {
        String itemName = itemField.getText();
        String amountText = amountField.getText();

        // Validate input
        if (itemName.isEmpty() || !Pattern.matches("\\d+", amountText)) {
            JOptionPane.showMessageDialog(null, "Please enter valid item name and amount (integer).");
            return;
        }

        int amount = Integer.parseInt(amountText);

        // Add data to table
        tableModel.addRow(new Object[]{itemName, amount});

        // Clear input fields
        itemField.setText("");
        amountField.setText("");
    }

    private void showPieChart() {
        pieDataset = new DefaultPieDataset();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String itemName = (String) tableModel.getValueAt(i, 0);
            int amount = (Integer) tableModel.getValueAt(i, 1);
            pieDataset.setValue(itemName, amount);
        }

        pieChart = ChartFactory.createPieChart("Pie Chart", pieDataset, true, true, false);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setExplodePercent("Category 2", 0.2);

        // Add the chart panel to the chartPanel
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(pieChart));
        chartPanel.revalidate();
    }

    private void reset() {
        tableModel.setRowCount(0);
        chartPanel.removeAll();
        chartPanel.revalidate();
    }

    public static void main(String[] args) {
        new DataVisualizationSystem();
    }
}