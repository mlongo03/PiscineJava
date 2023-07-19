// import java.io.*;
// import java.util.*;

// public class Program {
// 	public void main(String[] args) {
// 		if (args.length != 2) {
// 			System.out.println("Numero argomenti errato");
// 		}

// 	}
// }

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        String filePath_A = args[0];
        String filePath_B = args[1];

        try (FileReader fileReader = new FileReader(filePath_A);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            line = bufferedReader.readLine();
                System.out.println(line);

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}

