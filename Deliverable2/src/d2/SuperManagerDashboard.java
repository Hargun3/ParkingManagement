package d2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import com.csvreader.CsvWriter;
import com.csvreader.CsvReader;

public class SuperManagerDashboard extends JFrame {
    private DefaultTableModel tableModel;

    public SuperManagerDashboard() {
        setTitle("Super Manager Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Username", "Password"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable managerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(managerTable);

        JButton generateBtn = new JButton("Generate Managers");
        generateBtn.addActionListener(e -> generateManagers());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(generateBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void generateManagers() {
        tableModel.setRowCount(0); // Clear previous rows

        Set<Manager> managers = SuperManager.getInstance()
                .accessAccountGenerator(AccountGenerator.getInstance());

        int nextId = getNextUserId();

        CsvWriter writer = null;
        try {
            writer = new CsvWriter(new FileWriter("Deliverable2/user.csv", true), ',');

            for (Manager manager : managers) {
                tableModel.addRow(new Object[]{
                        manager.getUsername(),
                        manager.getPassword()
                });

                writer.write(String.valueOf(nextId++));
                writer.write(manager.getUsername());
                writer.write(manager.getPassword());
                writer.write("Manager");
                writer.write("approved");
                writer.endRecord();
            }

            JOptionPane.showMessageDialog(this, "✅ 5 managers generated and saved to user.csv!");

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to save manager accounts.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private int getNextUserId() {
        int maxId = 0;
        try {
            CsvReader reader = new CsvReader("Deliverable2/user.csv");
            reader.readHeaders();
            while (reader.readRecord()) {
                int id = Integer.parseInt(reader.get("id"));
                if (id > maxId) maxId = id;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SuperManagerDashboard dashboard = new SuperManagerDashboard();
            dashboard.setVisible(true);
        });
    }
}
