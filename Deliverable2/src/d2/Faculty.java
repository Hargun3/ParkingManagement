package d2;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileWriter;
import java.io.IOException;

public class Faculty implements Client {
    private int id;
    private String email;
    private String password;
    private Booking booking;
    private final Double rate = 10.0; // Staff parking rate

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public Booking getBooking() {
        return booking;
    }

    @Override
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean login(String email, String password) throws NumberFormatException, IOException {
        CsvReader reader = null;
        try {
            reader = new CsvReader("Deliverable2/user.csv");
            reader.readHeaders();

            while (reader.readRecord()) {
                String storedEmail = reader.get("email").trim();
                String storedPassword = reader.get("password").trim();
                String storedRole = reader.get("role").trim();
                String status = reader.get("status").trim();
                String storedId = reader.get("id").trim();

                System.out.println("Checking user: " + storedEmail + " | Role: " + storedRole + " | Status: " + status);
                System.out.println("Input Email: " + email + " | Input Password: " + password);

                if (!status.equalsIgnoreCase("approved") &&
                        (storedRole.equalsIgnoreCase("Student") || storedRole.equalsIgnoreCase("Faculty")
                                || storedRole.equalsIgnoreCase("Staff"))) {
                    if (!storedRole.equalsIgnoreCase("SuperManager")) {
                        System.out.println("? Account not yet approved by management.");
                        return false;
                    }
                }

                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    System.out.println(
                            "✅ Login successful for: " + email + " (ID: " + storedId + ", Role: " + storedRole + ")");
                    this.email = email;
                    this.password = password;
                    this.id = Integer.parseInt(storedId);
                    return true;
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        System.out.println("❌ Invalid login attempt.");
        return false;
    }

    @Override
    public boolean register(String email, String password, int id) throws NumberFormatException, IOException {
        if (!password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).+")) {
            System.out.println("❌ Weak password. Registration failed.");
            return false;
        }

        CsvReader reader = null;
        boolean userExists = false;

        try {
            reader = new CsvReader("Deliverable2/user.csv");
            while (reader.readRecord() && !reader.get("id").isEmpty()) {
                if (reader.get("id").equals(String.valueOf(id)) || reader.get("email").equals(email)) {
                    userExists = true;
                    break;
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        if (userExists) {
            System.out.println("❌ ID or email already exists. Registration failed.");
            return false;
        }

        CsvWriter output = null;
        try {
            output = new CsvWriter(new FileWriter("Deliverable2/user.csv", true), ',');
            output.write(String.valueOf(id));
            output.write(email);
            output.write(password);
            output.write("Staff");
            output.write("pending");
            output.endRecord();
        } finally {
            if (output != null) {
                output.close();
            }
        }

        System.out.println("✅ Registration successful for: " + email);
        return true;
    }

    @Override
    public boolean logout() {
        System.out.println("✅ User " + email + " logged out.");
        return true;
    }

    @Override
    public void initiateBooking() {
        this.booking = new Booking();
    }

    @Override
    public void initiatePayment() {
        Payment payment = new Payment();
    }
}
