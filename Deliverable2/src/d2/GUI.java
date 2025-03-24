package d2;

import javax.swing.*;

import com.csvreader.CsvReader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel dashboardLabel;
    private JLabel bookingInfoLabel;
    private ArrayList<ParkingSpace> allSpaces = new ArrayList<>();
    


    private Client currentUser;

    public GUI() {
        setTitle("Parking Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createBookingPanel(), "booking");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    private boolean isManager(String email) {
        for (Manager manager : SuperManager.getInstance().accessAccountGenerator(AccountGenerator.getInstance())) {
            if (manager.getUsername().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginBtn = new JButton("Login");
        JButton toRegisterBtn = new JButton("Register");

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPanel.add(emailField);
        inputPanel.add(passwordField);

        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toRegisterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(toRegisterBtn);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            Client user = authenticateUser(email, password);
            if (user != null) {
                currentUser = user;

                mainPanel.add(createDashboardPanel(), "dashboard");

                JOptionPane.showMessageDialog(this, "Login successful!");
                updateDashboard(user);
                cardLayout.show(mainPanel, "dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        toRegisterBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Register", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JLabel roleLabel = new JLabel("Role:");
        String[] roles = { "Student", "Faculty", "Staff", "Visitor" };
        JComboBox<String> roleDropdown = new JComboBox<>(roles);

        JButton registerBtn = new JButton("Register");
        JButton toLoginBtn = new JButton("Back to Login");

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleDropdown);

        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(toLoginBtn);

        registerBtn.addActionListener(e -> {
            String idText = idField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleDropdown.getSelectedItem();

            if (idText.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (registerUser(id, email, password, role)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(mainPanel, "login");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        toLoginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashboardLabel = new JLabel("Welcome, User!", SwingConstants.CENTER);
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bookingInfoLabel = new JLabel("", SwingConstants.CENTER);
        bookingInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //  Start a timer to auto-update the booking info label every 30 seconds
        new javax.swing.Timer(30000, e -> updateBookingInfoLabel(bookingInfoLabel)).start();

        JButton bookParkingBtn = new JButton("Book Parking");
        bookParkingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookParkingBtn.addActionListener(e -> cardLayout.show(mainPanel, "booking"));

        JButton payNowBtn = new JButton("Pay Now");
        payNowBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        payNowBtn.addActionListener(e -> handlePayment());
        //  Disable the button if booking is already paid
        if (currentUser.getBooking() != null && currentUser.getBooking().isPaid()) {
            payNowBtn.setEnabled(false);
            payNowBtn.setText("Already Paid ✅");
        }
                

        JButton extendBookingBtn = new JButton("Extend Booking");
        extendBookingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        extendBookingBtn.addActionListener(e -> {
            if (currentUser.getBooking() == null) {
                JOptionPane.showMessageDialog(this, "You don't have a booking to extend.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Enter number of hours to extend:");
            if (input == null || input.isEmpty())
                return;

            try {
                int hours = Integer.parseInt(input);
                if (hours <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number of hours.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                currentUser.getBooking().extendByHours(hours);
                JOptionPane.showMessageDialog(this, "Booking extended by " + hours + " hour(s).");
                updateBookingInfoLabel(bookingInfoLabel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBookingBtn = new JButton("Cancel Booking");
        cancelBookingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelBookingBtn.addActionListener(e -> {
            if (currentUser.getBooking() == null) {
                JOptionPane.showMessageDialog(this, "You don't have any booking to cancel.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                CancelBooking cancelBooking = new CancelBooking(currentUser.getBooking());
                if (cancelBooking.execute()) {
                    ParkingSpace space = currentUser.getBooking().getBookedSpace();
                    space.setState(new VacantState(space));

                    //  Reset button color
                    JPanel bookingPanel = (JPanel) mainPanel.getComponent(2);
                    for (Component comp : bookingPanel.getComponents()) {
                        if (comp instanceof JPanel) {
                            for (Component btn : ((JPanel) comp).getComponents()) {
                                if (btn instanceof JButton) {
                                    JButton button = (JButton) btn;
                                    if (button.getText().equals(space.getspace_Location())) {
                                        updateButtonState(space, button);
                                    }
                                }
                            }
                        }
                    }

                    currentUser.setBooking(null);
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                    updateBookingInfoLabel(bookingInfoLabel);
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to cancel booking.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error cancelling booking.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "login");
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(dashboardLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bookingInfoLabel);
        panel.add(Box.createVerticalStrut(10));

        if (!isManager(currentUser.getEmail()) && !"superadmin".equalsIgnoreCase(currentUser.getEmail())) {
            panel.add(bookParkingBtn);
            panel.add(Box.createVerticalStrut(10));
            panel.add(extendBookingBtn);
            panel.add(Box.createVerticalStrut(10));
            panel.add(cancelBookingBtn);
            panel.add(Box.createVerticalStrut(10));
            panel.add(payNowBtn);

        }

        if (isManager(currentUser.getEmail())) {
            JButton managerApprovalBtn = new JButton("Manager Approval");
            managerApprovalBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            managerApprovalBtn.addActionListener(e -> new ManagerApproval().setVisible(true));
            JButton disableAllBtn = new JButton("Disable All Parking Spaces");
            disableAllBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            disableAllBtn.addActionListener(e -> {
                for (ParkingSpace space : allSpaces) {
                    space.setState(new DisabledState(space));
                }
                JOptionPane.showMessageDialog(this, "🚫 All spaces disabled.");
            });
        
            JButton enableAllBtn = new JButton("Enable All Parking Spaces");
            enableAllBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            enableAllBtn.addActionListener(e -> {
                for (ParkingSpace space : allSpaces) {
                    space.setState(new VacantState(space));
                }
                JOptionPane.showMessageDialog(this, "✅ All spaces enabled.");
            });
        
            JButton toggleOneBtn = new JButton("Toggle Specific Space");
            toggleOneBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            toggleOneBtn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Enter space number (1-100):");
                try {
                    int idx = Integer.parseInt(input) - 1;
                    if (idx >= 0 && idx < allSpaces.size()) {
                        ParkingSpace s = allSpaces.get(idx);
                        if (s.getState() instanceof DisabledState) {
                            s.setState(new VacantState(s));
                        } else {
                            s.setState(new DisabledState(s));
                        }
                        
                        // Refresh buttons visually
                        Component[] comps = ((JPanel) ((JPanel) mainPanel.getComponent(2)).getComponent(1)).getComponents();
                        for (Component c : comps) {
                            if (c instanceof JButton && ((JButton) c).getText().equals(s.getspace_Location())) {
                                updateButtonState(s, (JButton) c);
                                break;
                            }
                        }
                        
                        JOptionPane.showMessageDialog(this, "Space " + (idx + 1) + " toggled.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid space number.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input.");
                }
            });
        
            panel.add(Box.createVerticalStrut(10));
            panel.add(disableAllBtn);
            panel.add(Box.createVerticalStrut(5));
            panel.add(enableAllBtn);
            panel.add(Box.createVerticalStrut(5));
            panel.add(toggleOneBtn);
        
            panel.add(Box.createVerticalStrut(10));
            panel.add(managerApprovalBtn);
        }

        if ("superadmin".equalsIgnoreCase(currentUser.getEmail())) {
            JButton superManagerBtn = new JButton("Super Manager Dashboard");
            superManagerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            superManagerBtn.addActionListener(e -> new SuperManagerDashboard().setVisible(true));
            panel.add(Box.createVerticalStrut(10));
            panel.add(superManagerBtn);
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(logoutBtn);

        // Update on load
        updateBookingInfoLabel(bookingInfoLabel);

        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Parking Booking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        ParkingLot parkingLot = new ParkingLot("Main Lot");
        JPanel parkingGrid = new JPanel(new GridLayout(10, 10, 5, 5));
        panel.add(parkingGrid, BorderLayout.CENTER);

        JButton backToDashboard = new JButton("Back to Dashboard");
        panel.add(backToDashboard, BorderLayout.SOUTH);

        for (int i = 1; i <= 100; i++) {
            ParkingSpace space = new ParkingSpace(parkingLot, "Space " + i);
            allSpaces.add(space); // Track each parking space
            JButton spaceButton = new JButton(space.getspace_Location());

            updateButtonState(space, spaceButton);
            int bookingId = i;

            spaceButton.addActionListener(e -> {
                if (space.getState() instanceof VacantState) {
                    if (currentUser.getBooking() != null) {
                        JOptionPane.showMessageDialog(this, "You already have a booking!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        String licensePlate = JOptionPane.showInputDialog(this,
                                "Enter your license plate:\n(Must be 2–10 characters: A-Z, 0-9, dashes or spaces)");
                        if (licensePlate == null || licensePlate.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "License plate is required.");
                            return;
                        }
                        if (!licensePlate.matches("^[A-Z0-9\\- ]{2,10}$")) {
                            JOptionPane.showMessageDialog(this, "Invalid license plate format.");
                            return;
                        }

                        Booking booking = new Booking();
                        booking.setId(bookingId);
                        Date start = new Date();
                        booking.setStartTime(start);
                        //  Set end time to 1 hour later by default
                        booking.setExitTime(new Date(start.getTime() + 60L * 60 * 1000));

                        booking.setBookedSpace(space);
                        booking.setLicensePlate(licensePlate);

                        BookParking bookParking = new BookParking(booking, space, currentUser.getEmail());
                        bookParking.execute();

                        currentUser.setBooking(booking);
                        space.setState(new OccupiedState(space));
                        updateButtonState(space, spaceButton);
                        updateBookingInfoLabelFromOutside();
                        JOptionPane.showMessageDialog(this, "Booked " + space.getspace_Location());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Booking failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "This space is already booked!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            parkingGrid.add(spaceButton);
        }

        backToDashboard.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        backToDashboard.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));

        return panel;
    }

    private void updateButtonState(ParkingSpace space, JButton button) {
        if (space.getState() instanceof VacantState) {
            button.setBackground(Color.GREEN);
            button.setEnabled(true);
        } else if (space.getState() instanceof OccupiedState) {
            button.setBackground(Color.RED);
            button.setEnabled(false);
        } else if (space.getState() instanceof DisabledState) {
            button.setBackground(Color.DARK_GRAY);
            button.setEnabled(false);
        }
    }
    
    private void handlePayment() {
        if (currentUser.getBooking() == null) {
            JOptionPane.showMessageDialog(this, "No active booking to pay for.");
            return;
        
        }
    
        Date now = new Date();
        Date exit = currentUser.getBooking().getExitTime();
        double rate = currentUser.getRate();
    
        long durationMs = exit.getTime() - currentUser.getBooking().getStartTime().getTime();
        double hours = durationMs / (1000.0 * 60 * 60);
        double cost = Math.ceil(hours * rate);
    
        String[] options = {"Credit", "Debit", "PayPal"};
        String method = (String) JOptionPane.showInputDialog(
                this, "Choose payment method:", "Payment",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
        if (method == null) return;
    
        boolean success = false;
    
        switch (method) {
            case "Credit":
            case "Debit":
                String cardNumber = JOptionPane.showInputDialog(this, "Enter card number:");
                String exp = JOptionPane.showInputDialog(this, "Enter expiry date (MM/YYYY):");
                String cvv = JOptionPane.showInputDialog(this, "Enter CVV:");
                if (cardNumber != null && exp != null && cvv != null) {
                    success = validateCardFull(cardNumber.trim(), exp.trim(), cvv.trim());
                }
                break; // ✅ Don't forget this!
    
            case "PayPal":
                String email = JOptionPane.showInputDialog(this, "Enter PayPal email:");
                String password = JOptionPane.showInputDialog(this, "Enter PayPal password:");
                if (validatePaypal(email, password)) success = true;
                break;
        }
    
        if (success) {
            Booking b = currentUser.getBooking();
        
            String receipt = "✅ Payment Successful!\n\n" +
                             "👤 Email: " + currentUser.getEmail() + "\n" +
                             "🆔 Booking ID: " + b.getId() + "\n" +
                             "📍 Space: " + b.getBookedSpace().getspace_Location() + "\n" +
                             "🚘 License Plate: " + b.getLicensePlate() + "\n" +
                             "⏰ Start Time: " + b.getStartTime() + "\n" +
                             "⏳ End Time: " + b.getExitTime() + "\n" +
                             "💳 Payment Method: " + method + "\n" +
                             "💵 Total Paid: $" + String.format("%.2f", cost);
        
            JOptionPane.showMessageDialog(this, receipt, "🧾 Receipt", JOptionPane.INFORMATION_MESSAGE);
        
            b.setPaid(true); //  mark booking as paid
        }        
         else {
            JOptionPane.showMessageDialog(this, "❌ Payment failed. Invalid credentials.", "Payment", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private boolean validateCardFull(String inputCardNumber, String inputExp, String inputCVV) {
    try {
        com.csvreader.CsvReader reader = new com.csvreader.CsvReader("Deliverable2/cards.csv");
        reader.readHeaders();

        while (reader.readRecord()) {
            String csvCardNumber = reader.get("number").trim();
            String csvExp = reader.get("exp_date").trim();
            String csvCVV = reader.get("CVV").trim();

            if (csvCardNumber.equals(inputCardNumber) &&
                csvExp.equalsIgnoreCase(inputExp) &&
                csvCVV.equals(inputCVV)) {

                System.out.println("✅ Card validated: " + csvCardNumber);
                reader.close();
                return true;
            }
        }
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    
    
    private boolean validatePaypal(String email, String password) {
        try {
            com.csvreader.CsvReader reader = new com.csvreader.CsvReader("Deliverable2/paypal.csv");
            reader.readHeaders();
            while (reader.readRecord()) {
                if (reader.get("email").equals(email) &&
                    reader.get("password").equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

    private void updateDashboard(Client user) {
        dashboardLabel.setText("<html>Welcome, " + user.getEmail() + "<br>Role: " + user.getClass().getSimpleName() +
                "<br>Rate: $" + user.getRate() + "/hour</html>");
    }

    private Client authenticateUser(String email, String password) {
        try {
            CsvReader reader = new CsvReader("Deliverable2/user.csv");
            reader.readHeaders();

            while (reader.readRecord()) {
                String storedEmail = reader.get(4).trim();
                String storedPassword = reader.get("password").trim();
                String storedRole = reader.get("role").trim();
                String status = reader.get("status").trim();

                //  Superadmin check
                if (storedEmail.equals(email) && storedPassword.equals(password)
                        && storedRole.equalsIgnoreCase("SuperManager")
                        && status.equalsIgnoreCase("approved")) {

                    System.out.println("✅ Superadmin login detected");
                    return new Client() {
                        public boolean login(String e, String p) {
                            return true;
                        }

                        public boolean logout() {
                            return true;
                        }

                        public String getEmail() {
                            return storedEmail;
                        }

                        public double getRate() {
                            return 0.0;
                        }

                        public Booking getBooking() {
                            return null;
                        }

                        public void setBooking(Booking b) {
                        }

                        public void initiateBooking() {
                        }

                        public void initiatePayment() {
                        }

                        public boolean register(String e, String p, int id) {
                            return false;
                        }
                    };
                }

                //  Manager check
                if (storedEmail.equals(email) && storedPassword.equals(password)
                        && storedRole.equalsIgnoreCase("Manager")
                        && status.equalsIgnoreCase("approved")) {

                    System.out.println("✅ Manager login detected");
                    return new Client() {
                        public boolean login(String e, String p) {
                            return true;
                        }

                        public boolean logout() {
                            return true;
                        }

                        public String getEmail() {
                            return storedEmail;
                        }

                        public double getRate() {
                            return 0.0;
                        }

                        public Booking getBooking() {
                            return null;
                        }

                        public void setBooking(Booking b) {
                        }

                        public void initiateBooking() {
                        }

                        public void initiatePayment() {
                        }

                        public boolean register(String e, String p, int id) {
                            return false;
                        }
                    };
                }
            }

            reader.close();

            //  Then check normal users
            Client[] users = {
                    new Student(),
                    new Faculty(),
                    new Staff(),
                    new Visitor()
            };

            for (Client user : users) {
                if (user.login(email, password)) {
                    Booking previousBooking = loadBookingFromCSV(email);
                    if (previousBooking != null) {
                        user.setBooking(previousBooking);
                    }
                    return user;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private Booking loadBookingFromCSV(String email) {
        try {
            com.csvreader.CsvReader reader = new com.csvreader.CsvReader("Deliverable2/booking.csv");
            // reader.readHeaders(); // ❌ REMOVE THIS
    
            while (reader.readRecord()) {
                String rowEmail = reader.get(4).trim();
                if (rowEmail.equalsIgnoreCase(email)) {
                    Booking b = new Booking();
                    b.setId(Integer.parseInt(reader.get(0).trim()));
                    b.setStartTime(new Date(reader.get(1).trim()));
                    b.setBookedSpace(new ParkingSpace(new ParkingLot("Main Lot"), reader.get(2).trim()));
                    b.setLicensePlate(reader.get(3).trim());
                    b.setExitTime(new Date(b.getStartTime().getTime() + 60L * 60 * 1000)); // assume 1 hour
                    return b;
                }
            }
    
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    private void updateBookingInfoLabel(JLabel label) {
        if (currentUser != null && currentUser.getBooking() != null) {
            Date now = new Date();
            Date end = currentUser.getBooking().getExitTime();

            if (end == null) {
                label.setText("Booking active. End time not set.");
                return;
            }

            long diffMillis = end.getTime() - now.getTime();
            if (diffMillis <= 0) {
                label.setText("⚠️ Booking expired.");
            } else {
                long minutes = diffMillis / (1000 * 60);
                long hours = minutes / 60;
                minutes %= 60;

                label.setText("Booking ends at: " + end +
                        " | Remaining: " + hours + "h " + minutes + "m");
            }
        } else {
            label.setText("No active booking.");
        }
    }

    private void updateBookingInfoLabelFromOutside() {
        if (bookingInfoLabel != null) {
            updateBookingInfoLabel(bookingInfoLabel);
        }
    }

    private boolean registerUser(int id, String email, String password, String role) {
        try {
            Client user;
            switch (role) {
                case "Student":
                    user = new Student();
                    break;
                case "Faculty":
                    user = new Faculty();
                    break;
                case "Staff":
                    user = new Staff();
                    break;
                case "Visitor":
                    user = new Visitor();
                    break;
                default:
                    return false;
            }

            return user.register(email, password, id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
