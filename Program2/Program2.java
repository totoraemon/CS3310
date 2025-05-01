/**************************************************************/
/* Joseline Ly                                                */
/* Login ID: 017241510                                        */
/* CS 3310, Spring 2025                                       */
/* Programming Assignment 2                                   */
/* Program2 class: takes in a file and returns a .txt file    */
/* with lists of anagrams                                     */
/**************************************************************/

import java.io.*;
import java.util.*;
import java.text.Normalizer;

public class Program2 {

    /*************************************************/
    /* Method: main                                  */
    /* Purpose: call method fileRead()               */
    /* Parameters:                                   */
    /*  String[] args: unused, input of file name    */
    /* Returns: void: no return                      */
    /*************************************************/
    public static void main (String[] args) {
        try {
            fileRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*************************************************************************/
    /* Method: fileRead                                                      */
    /* Purpose: Read file input/ouput names and apply getCharCount() and     */
    /*          normalize strings with removeAccents                         */
    /* Parameters:                                                           */
    /* Returns: void:       no return                                        */
    /*************************************************************************/
    
    @SuppressWarnings({ "resource", "unused" })
    /**
     * Suppress "resource" warning for HashMap raw type
     * Suppress "unused" warning for variable k in lambda expression
     * @throws IOException
     */
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

        /**
         * For each list of strings in Map groups, check if its size is greater than 1.
         * If it is, print the entire list of anagrams.
         * Otherwise, skip it since the group is not an anagram. 
        */
        for (List<String> group : groups.values()) {
            if (group.size() > 1) {
                writer.write(group.toString());
                writer.newLine(); // Move to the next line
            }            
        }
        scnr.close();
    }

    /*************************************************************************/
    /* Method: getCharCount                                                  */
    /* Purpose: Obtain frequency count of characters in string format        */
    /* Parameters:                                                           */
    /*  String input:         word to be counted                             */
    /* Returns: string:       string format of frequency count per character */
    /*************************************************************************/
    private static String getCharCount(String input) {
        // Initial size for frequency array for characters a - z.
        int[] freq = new int[27];

        // For each character in the string, create a frequency count.
        for (char c : input.toCharArray()) {
            freq[c - 'a']++;
        }

        // Convert frequency array into string of numbers and return string.
        return Arrays.toString(freq);
    }

    /*************************************************************************/
    /* Method: removeAccents                                                 */
    /* Purpose: Remove accents from characters to normalize values           */
    /* Parameters:                                                           */
    /*  String input:         word to be modified                            */
    /* Returns: string:       string without accented characters             */
    /*************************************************************************/
    private static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
    
}