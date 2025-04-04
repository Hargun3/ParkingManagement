package d2;

import com.csvreader.CsvReader;
import java.io.IOException;

public class Manager implements Client {
    private String email;
    private String password;

    //  Constructor used by AccountGenerator when creating managers
    public Manager(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //  Default constructor (required for login system that does new Manager())
    public Manager() {
    }

    @Override
    public boolean login(String email, String password) throws IOException {
        CsvReader reader = new CsvReader("Deliverable2/user.csv");
        reader.readHeaders();

        while (reader.readRecord()) {
            String storedEmail = reader.get("email").trim();
            String storedPassword = reader.get("password").trim();
            String role = reader.get("role").trim();
            String status = reader.get("status").trim();
            String storedId = reader.get("id").trim();

            if (storedEmail.equals(email) &&
                    storedPassword.equals(password) &&
                    role.equalsIgnoreCase("Manager") &&
                    status.equalsIgnoreCase("approved")) {

                this.email = email;
                this.password = password;
                System.out.println(
                        "✅ Manager login successful: " + email + " (ID: " + storedId + ", Role: " + role + ")");
                return true;
            }
        }

        System.out.println("❌ Manager login failed.");
        return false;
    }

    @Override
    public boolean register(String email, String password, int id) {
        return false; // Managers are generated by superadmin only
    }

    @Override
    public boolean logout() {
        System.out.println("✅ Manager " + email + " logged out.");
        return true;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return email; // Used in isManager() check
    }

    public String getPassword() {
        return password;
    }

    @Override
    public double getRate() {
        return 0; // Managers don't pay
    }

    @Override
    public Booking getBooking() {
        return null;
    }

    @Override
    public void setBooking(Booking booking) {
        // Managers don’t need bookings
    }

    @Override
    public void initiateBooking() {
        // Not applicable
    }

    @Override
    public void initiatePayment() {
        // Not applicable
    }
}
