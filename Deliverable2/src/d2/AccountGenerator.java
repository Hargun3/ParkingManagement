package d2;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;


public class AccountGenerator {
	private static AccountGenerator instance;
	private Set<Manager> managers;

    private AccountGenerator() {
    	this.managers = new HashSet<>();
    }

    public static AccountGenerator getInstance() {
        if (instance == null) {
            instance = new AccountGenerator();
        }
        return instance;
    }

    private Set<Manager> generateAccounts() {
    	Set<Manager> generatedManagers = new HashSet<>();
        for (int i = 1; i <= 5; i++) {
            String username = "Manager" + i;
            String password = generateStrongPassword(12); 
            generatedManagers.add(new Manager(username, password));
        }
        this.managers = generatedManagers; 
        return managers;
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

    public Set<Manager> accessForSuperManager(SuperManager superManager) {
        return generateAccounts();
    }

}
