import java.io.*;
import java.util.*;

public class Test {
    public static void main (String[] args) {
        fileRead();
    }

    public static void fileRead() {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Enter a file: ");
        String filename = scnr.nextLine();
        System.out.println(filename);
        scnr.close();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader((new FileReader(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (Exception e) {
            System.out.print("Error while opening file.");
        }
        
    }
}