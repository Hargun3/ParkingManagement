package d2;

public class StudentCreator implements ClientCreator {

	@Override
	public Client createClient() {
		return new Student();
	}

}
