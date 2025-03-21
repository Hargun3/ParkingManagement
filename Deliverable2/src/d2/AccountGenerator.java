package d2;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import com.csvreader.CsvWriter;
import com.csvreader.CsvReader;

public class AccountGenerator {
	private static AccountGenerator instance;
	private Set<Manager> managers;
	private final String path = "managers.csv";


    private AccountGenerator() {
    	this.managers = new HashSet<>();
    }

    public static AccountGenerator getInstance() {
        if (instance == null) {
            instance = new AccountGenerator();
        }
        return instance;
    }

    public void load() throws Exception {
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
            String username = reader.get("username");
            String password = reader.get("password");
            Manager manager = new Manager(username, password);
            managers.add(manager);
        }
        reader.close();
    }
    
    public void update() throws Exception {
        CsvWriter writer = new CsvWriter(new FileWriter(path, false), ',');
        writer.write("username");
        writer.write("password");
        writer.endRecord();
        for (Manager m : managers) {
            writer.write(m.getUsername());
            writer.write(m.getPassword());
            writer.endRecord();
        }
        writer.close();
    }

    private Set<Manager> generateAccounts() throws Exception {
    	Set<Manager> generatedManagers = new HashSet<>();
        load(); 
        Set<String> existingUsernames = new HashSet<>();
        for (Manager m : managers) {
            existingUsernames.add(m.getUsername());
        }
        int count = 0;
        while (count < 5) {
            String username = generateManagerUsername(existingUsernames);
            String password = generateStrongPassword(12);
            Manager manager = new Manager(username, password);
            generatedManagers.add(manager);
            managers.add(manager);
            existingUsernames.add(username);
            count++;
        }
        update(); 
        return generatedManagers;
    }
    
    private String generateStrongPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }

    public Set<Manager> accessForSuperManager(SuperManager superManager) throws Exception {
        return generateAccounts();
    }
    
    private String generateManagerUsername(Set<String> existingUsernames) {
        Random random = new Random();
        String username;
        do {
            StringBuilder sb = new StringBuilder("Manager");
            for (int i = 0; i < 9; i++) {
                sb.append(random.nextInt(10));
            }
            username = sb.toString();
        } while (existingUsernames.contains(username)); 

        return username;
    }

}
