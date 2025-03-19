package d2;

public class FacultyCreator implements ClientCreator{
	
	@Override
	public Client createClient() {
		return new Faculty();
	}
}
