package d2;

public class StaffCreator implements ClientCreator{
	
	@Override
	public Client createClient() {
		return new Staff();
	}

}
