import java.io.*;
import java.util.*;

class Program {
	static public void main(String[] args) {
		signatures sig = new signatures();
		Scanner scanner = new Scanner(System.in);
		ArrayList<char[]> result = new ArrayList<>();
		String line;

		sig.loadSignatures();
		try (FileOutputStream file = new FileOutputStream("result.txt")) {
			line = scanner.nextLine();
			while (!line.equals("42")) {
				result = sig.compareFile(line);
				line = scanner.nextLine();
			}
			for (char[] proc : result) {
				byte[] bytes = new String(proc).getBytes("UTF-8");
				file.write(bytes);
				file.write(10);
			}
			file.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Error");
		}
		catch (IOException e) {
			System.err.println("Error");
		}
	}
}
