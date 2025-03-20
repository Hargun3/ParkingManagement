package d2;
import java.util.HashSet;
import java.util.Set;

public class SuperManager {
	private static SuperManager instance;
    private Set<Manager> allManagers;

    private SuperManager() {
        this.allManagers = new HashSet<>();
    }

    public static SuperManager getInstance() {
        if (instance == null) {
            instance = new SuperManager();
        }
        return instance;
    }

    public Set<Manager> accessAccountGenerator(AccountGenerator accountGenerator) {
    	this.allManagers = accountGenerator.accessForSuperManager(this);
        return allManagers;
    }

}
