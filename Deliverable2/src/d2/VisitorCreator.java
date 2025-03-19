package d2;

public class VisitorCreator implements ClientCreator{
	
	@Override
	public Client createClient() {
		return new Visitor();
	}

}
