package d2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class ManagerApproval extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> usersData;

    public ManagerApproval() {
        setTitle("Manager Approval Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Email", "Role", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        loadPendingUsers();

        JButton approveBtn = new JButton("Approve Selected");
        approveBtn.addActionListener(e -> approveSelectedUser());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(approveBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPendingUsers() {
        usersData = new ArrayList<>();
        tableModel.setRowCount(0);

        try {
            CsvReader reader = new CsvReader("Deliverable2/user.csv");
            reader.readHeaders();

            while (reader.readRecord()) {
                String id = reader.get("id");
                String email = reader.get("email");
                String password = reader.get("password");
                String role = reader.get("role");
                String status = reader.get("status");

                // Add to memory
                usersData.add(new String[]{id, email, password, role, status});

                if (status.equalsIgnoreCase("pending") &&
                (role.equalsIgnoreCase("Student") ||
                role.equalsIgnoreCase("Faculty") ||
                role.equalsIgnoreCase("Staff"))) {

                    tableModel.addRow(new Object[]{id, email, role, status});
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void approveSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedEmail = tableModel.getValueAt(selectedRow, 1).toString();

        // Update status in memory
        for (String[] user : usersData) {
            if (user[1].equals(selectedEmail)) {
                user[4] = "approved"; // ✅ change status
            }
        }

        // Rewrite the entire CSV with updated statuses
        try {
            CsvWriter writer = new CsvWriter(new FileWriter("Deliverable2/user.csv", false), ',');
            writer.writeRecord(new String[]{"id", "email", "password", "role", "status"});

            for (String[] user : usersData) {
                writer.writeRecord(user);
            }
            writer.close();

            JOptionPane.showMessageDialog(this, "User approved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPendingUsers(); // Reload table
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManagerApproval managerPanel = new ManagerApproval();
            managerPanel.setVisible(true);
        });
    }
}
