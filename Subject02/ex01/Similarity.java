import java.io.*;
import java.util.*;

public class Similarity {

    public Similarity() {
    }

    public LinkedHashMap<String, Integer> countWords(String filePath, Set<String> dictionary) {

        LinkedHashMap<String, Integer> wordCount = new LinkedHashMap<>();

        for (String word : dictionary) {
            wordCount.put(word, 0);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String      line;
            String[]    words;
            String      lowercaseWord;

            while ((line = reader.readLine()) != null) {
                words = line.split(" ");
                for (String word : words) {
                    if (!word.isEmpty() && dictionary.contains(word.toLowerCase())) {
                        lowercaseWord = word.toLowerCase();
                        wordCount.put(lowercaseWord, wordCount.get(lowercaseWord) + 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (wordCount);
    }

    public double calculateSimilarity(LinkedHashMap<String, Integer> A, LinkedHashMap<String, Integer> B) {

        double numerator = 0;
        double denominator = 0;
        double normA = 0;
        double normB = 0;

        for (String key : A.keySet()) {
            if (B.containsKey(key)) {
                numerator += A.get(key) * B.get(key);
            }
            normA += Math.pow(A.get(key), 2);
        }
        for (int value : B.values()) {
            normB += Math.pow(value, 2);
        }
        denominator = Math.sqrt(normA) * Math.sqrt(normB);
        return ((numerator == 0 || denominator == 0) ? 0 : numerator / denominator);
    }
}
