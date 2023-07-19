import java.io.*;
import java.util.*;

public class Program {

    private static final String DICTIONARY = "dictionary.txt";

    public static void main(String[] args) {

        Dictionary dictionary = new Dictionary();

        if (args.length != 2) {
            System.err.println("Error: put only two files");
            System.exit(-1);
        }

        try (FileReader inputA = new FileReader(args[0]);
                FileReader inputB = new FileReader(args[1]);
                BufferedReader buffInputA = new BufferedReader(inputA);
                BufferedReader buffInputB = new BufferedReader(inputB);
                FileOutputStream fileoutput = new FileOutputStream(DICTIONARY)) {
                
                dictionary.createDictionary(args[0], args[1]);
                dictionary.saveWord(DICTIONARY);

                Similarity similarity = new Similarity();

                LinkedHashMap<String, Integer> wordCountA = similarity.countWords(args[0], dictionary.createDictionary(args[0], args[1]));

                LinkedHashMap<String, Integer> wordCountB = similarity.countWords(args[1], dictionary.createDictionary(args[0], args[1]));

                System.out.println("Similarity = " + Math.floor(similarity.calculateSimilarity(wordCountA, wordCountB) * 100) / 100);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}