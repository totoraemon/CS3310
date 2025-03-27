import java.io.*;
import java.util.*;
import java.text.Normalizer;

public class Program2 {
    public static void main (String[] args) {
        try {
            fileRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Warning "resource" for HashMap raw type
    // Warning "unused" for variable k in lambda expression
    @SuppressWarnings({ "resource", "unused" })
    public static void fileRead() throws IOException {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Enter an input file: ");
        String filename = scnr.nextLine();
        BufferedReader reader = null;

        System.out.print("Enter an output file: ");
        String outputFilename = scnr.nextLine();
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));

        Map<String, List<String>> groups = new HashMap();
        String word = "";
        String freqCount = "";

        try {
            reader = new BufferedReader((new FileReader(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                word = line.toLowerCase().replace("'", "").trim();
                word = removeAccents(word);
                if (word.isEmpty()) {
                    continue;
                }
                freqCount = getCharCount(word);
                groups.computeIfAbsent(freqCount, k -> new ArrayList<>()).add(line);
            }
        }
        
        catch (Exception e) {
            System.out.print("Error while opening file.");
            e.printStackTrace();
        }

        // For each list of strings in Map groups, check if its size is greater than 1.
        // If it is, print the entire list of anagrams. Otherwise, skip since group is not anagram.
        for (List<String> group : groups.values()) {
            if (group.size() > 1) {
                writer.write(group.toString());
                writer.newLine(); // Move to the next line
            }            
        }
        scnr.close();
    }

    private static String getCharCount(String s) {
        // Frequency array for characters a - z
        int[] freq = new int[27];

        // For each character in the string, create a frequency count
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Convert frequency array into string of numbers and return
        return Arrays.toString(freq);
    }

    private static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
    
}