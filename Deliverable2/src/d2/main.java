package d2;

import java.io.FileWriter;
import java.io.IOException;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
				CsvReader reader = new CsvReader("user.csv"); 
				Student student = new Student();
				while(reader.readRecord() && reader.get("id") != ""){ 
					//name,id,email,password
					student.setId(Integer.valueOf(reader.get("id")));
					student.setEmail(reader.get("email"));
					student.setPassword(reader.get("password"));
				}
				CsvWriter output = new CsvWriter(new FileWriter("user.csv", true), ',');
				output.write("test");
				output.write("test");
				output.write("test");
				output.endRecord();
				output.close();
				System.out.println("done");
	}
}
