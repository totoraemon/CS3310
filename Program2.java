import java.io.*;
import java.util.*;

public class Program2 {
    public static void main (String[] args) {
        fileRead();
    }

    public static void fileRead() {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Enter a file: ");
        String filename = scnr.nextLine();
        BufferedReader reader = null;

        Map<String, List<String>> groups = new HashMap();
        String word = "";
        String freqCount = "";

        try {
            reader = new BufferedReader((new FileReader(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                word = line.toLowerCase().replace("'", "").trim();
                if (word.isEmpty()) {
                    continue;
                }
                freqCount = getCharCount(word);

                // groups.computeIfAbsent(freqCount, k -> new ArrayList<>()).add(line);
                System.out.println(word);
            }
        }
        
        catch (Exception e) {
            System.out.print("Error while opening file.");
        }

        // For each list of strings in Map groups, check if its size is greater than 1.
        // If it is, print the entire list of anagrams.
        for (List<String> group : groups.values()) {
            if (group.size() > 1) {
                System.out.println(group);
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
    
}