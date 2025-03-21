package d2;

public class Manager {
	private String manager_Username;
    private String manager_Password;

    public Manager(String manager_Username, String manager_Password) {
        this.manager_Username = manager_Username;
        this.manager_Password = manager_Password;
    }

    public boolean managerLogin(String manager_Username, String manager_Password) {
        return this.manager_Username.equals(manager_Username) && this.manager_Password.equals(manager_Password);
    }

    public void managerLogout() {
        System.out.println("Manager " + manager_Username + " logged out.");
    }

	public String getUsername() {
    	return manager_Username;
    }
    
    public String getPassword() {
    	return manager_Password;
    }

}
