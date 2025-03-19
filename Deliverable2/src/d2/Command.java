package d2;

import java.io.IOException;

public interface Command {
	
	public boolean execute() throws NumberFormatException, IOException;
	public boolean undo() throws NumberFormatException, IOException;
	
}
