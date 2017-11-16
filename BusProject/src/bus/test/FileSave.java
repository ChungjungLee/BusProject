package bus.test;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileSave {
	public void saveFile(String fileName, String data) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(data.replaceAll("\n", "\r\n"));
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
