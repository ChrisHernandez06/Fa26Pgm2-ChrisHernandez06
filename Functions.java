
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Functions {

    public static void loadData(Map<Integer, ArrayList<String>> TVList){
        String fileName = "tv.csv";
        
        //read the file & load the map
        try{
            Scanner inFile = new Scanner(new File(fileName));
            
            while (inFile.hasNext()){
                String inputRecord = inFile.nextLine();
                try{
                //set up data 


                //add to map
                }
                catch (Exception e){
                    System.out.println("Error in input record");
                }
            }
            inFile.close();
        }
        catch (Exception e){
            System.out.println("Error in input record");
        }
    }

    public static String getMenuItem(Scanner input) {
        String choice = " ";
        System.out.println("\nACTIONS FOR TVSHOW MAP");
        System.out.println("A: Add a Show ");
        System.out.println("D: Delete a Show ");
        System.out.println("K: Print All Keys (Durations) to Report");
        System.out.println("P: Print Map Listing to Report ");
        System.out.println("S: Print Specific Key (Duration) Listing to Report ");
        System.out.println("Q: Quit ");
        System.out.print("Please enter your choice: ");
        choice = input.nextLine().toUpperCase().trim();

        while (!( choice.equals("A") || choice.equals("D") ||
                  choice.equals("K") || choice.equals("P") ||
                  choice.equals("S") || choice.equals("Q"))){
            System.out.print("You entered an invalid value. Please enter a valid choice: ");
            choice = input.nextLine().toUpperCase().trim();
        }
        System.out.println();
        return choice;
    }

}
