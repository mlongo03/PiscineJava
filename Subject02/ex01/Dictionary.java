import java.io.*;
import java.util.*;

public class Dictionary {

	private Set<String> dictionary = new TreeSet<>();

	public Dictionary() {
	}

	public Set<String> getDictionary() {
		return (this.dictionary);
	}

	public Set<String> createDictionary(String fileA, String fileB) {

		try (BufferedReader readerA = new BufferedReader(new FileReader(fileA));
				BufferedReader readerB = new BufferedReader(new FileReader(fileB))) {

			String line;
			String[] words;

			while ((line = readerA.readLine()) != null) {
				words = line.split(" ");
				for (String word : words) {
					if (!word.isEmpty()) {
						dictionary.add(word.toLowerCase());
					}
				}
			}
			while ((line = readerB.readLine()) != null) {
				words = line.split(" ");
				for (String word : words) {
					if (!word.isEmpty()) {
						dictionary.add(word.toLowerCase());
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return (dictionary);
	}

	public void saveWord(String path) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

			for (String word : dictionary) {
				writer.write(word);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
