
import java.util.*;
import java.io.*;

public class Functions {

    // PRE: TVList has been initialized and tv.csv exists
    // POST: TVList contains each duration as a key and a list of show names with that duration
    public static void loadData(Map<Integer, ArrayList<String>> TVList){
        String fileName = "tv.csv";
        
        //read the file & load the map
        try{
            Scanner inFile = new Scanner(new File(fileName));
            
            while (inFile.hasNext()){
                String inputRecord = inFile.nextLine();
                try{
                    //set up data 
                    int firstComma = inputRecord.indexOf(',');

                    // LLM helped me figure out how to handle parsing titles
                    // that also include commas within the title itself

                    //parse the duration from the substring before the first comma
                    int duration = Integer.parseInt(
                            inputRecord.substring(0, firstComma).trim()
                    );

                    int showStart = firstComma + 1;
                    String showName;

                    //parse the show name from the substring after the first comma
                    if (inputRecord.charAt(showStart) == '"') {
                        int closingQuote = inputRecord.indexOf("\",", showStart);
                        showName = inputRecord.substring(showStart + 1, closingQuote);
                    }
                    else {
                        int secondComma = inputRecord.indexOf(',', showStart);
                        showName = inputRecord.substring(showStart, secondComma).trim();
                    }

                    //add to map
                    if (TVList.containsKey(duration)) {
                        TVList.get(duration).add(showName);
                    }
                    else {
                        ArrayList<String> shows = new ArrayList<>();
                        shows.add(showName);
                        TVList.put(duration, shows);
                    }
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

    // PRE: input has been initialized
    // POST: Returns a valid menu choice of A, D, K, P, S, or Q
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

    //LLM Used to quickly set up skeletons for each required method

    // PRE: TVList and out have been initialized
    // POST: All keys in TVList are written to the report file
    public static void printKeys(Map<Integer, ArrayList<String>> TVList, PrintWriter out) {
        out.println("TV SHOW DURATIONS:");

        // Prints all the keys (durations) in the TVList to the report file
        // LLM helped me find keySet() method to iterate over the keys of the map
        for (Integer key : TVList.keySet()) {
            out.println(key);
        }

        out.println();
        out.flush();

        System.out.println("All keys were printed to the report file.");
    }


    // PRE: TVList and out have been initialized
    // POST: The entire map is written to the report file
    public static void printMap(Map<Integer, ArrayList<String>> TVList, PrintWriter out) {
        out.println("TV SHOW MAP:");

        // Prints the entire map to the report file
        for (Integer key : TVList.keySet()) {

            out.println("Duration: " + key);

            for (String show : TVList.get(key)) {
                out.println("   " + show);
            }

            out.println();
        }

        out.flush();

        System.out.println("The map listing was printed to the report file.");
    }


    // PRE: TVList, input, and out have been initialized
    // POST: Shows for the requested duration are written to the report file
    public static void printSpecificKey(Map<Integer, ArrayList<String>> TVList, Scanner input, PrintWriter out) {

        int duration;

        while (true) {
            // Prompt the user to enter a valid duration for the TV show
            try {
                System.out.print("Enter the duration to list: ");
                duration = Integer.parseInt(input.nextLine().trim());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid duration. Please enter an integer.");
            }
        }

        // Check if the requested duration exists in the map and print the corresponding shows
        if (TVList.containsKey(duration)) {

            out.println("Shows with duration " + duration + ":");

            for (String show : TVList.get(duration)) {
                out.println("   " + show);
            }

            out.println();
            out.flush();

            System.out.println(
                "Shows with the duration of " + duration +
                " were written to the report file."
            );
        }
        // If the requested duration does not exist in the map, print a message indicating no shows were found
        else {
            System.out.println(
                "No shows were found with the duration of " + duration + "."
            );
        }
    }

    // LLM helped me walk through and setup addShow / deleteShow methods

    // PRE: TVList, input, and out have been initialized
    // POST: A new show is added to the appropriate duration in TVList
    public static void addShow(Map<Integer, ArrayList<String>> TVList, Scanner input, PrintWriter out) {

        int duration;

        // Get a valid integer duration
        while (true) {
            try {
                System.out.print("Enter the duration: ");
                duration = Integer.parseInt(input.nextLine().trim());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid duration. Please enter an integer.");
            }
        }

        // Get show name
        System.out.print("Enter the name of the show: ");
        String showName = input.nextLine().trim();

        // If the duration already exists, add to its list
        if (TVList.containsKey(duration)) {
            TVList.get(duration).add(showName);
        }
        else {
            ArrayList<String> shows = new ArrayList<>();
            shows.add(showName);
            TVList.put(duration, shows);
        }

        String message = "A: This new show was added to the map: "
                + showName + " with the duration of "
                + duration + " years.";

        System.out.println(message);
        out.println(message);
        out.flush();
    }


    // PRE: TVList, input, and out have been initialized
    // POST: The requested show is removed if found
    public static void deleteShow(Map<Integer, ArrayList<String>> TVList, Scanner input, PrintWriter out) {

        System.out.print("Enter the show name to delete: ");
        String showName = input.nextLine().trim();

        boolean found = false;
        Integer foundKey = null;
        String actualShowName = "";

        // Iterate through the map to find the show to delete
        for (Map.Entry<Integer, ArrayList<String>> entry : TVList.entrySet()) {

            ArrayList<String> shows = entry.getValue();

            // Iterate through the list of shows for this duration to find the show to delete
            for (int i = 0; i < shows.size(); i++) {

                // When the current show matches the show name to delete (non-case sensitive)
                if (shows.get(i).equalsIgnoreCase(showName)) {

                    actualShowName = shows.get(i);
                    shows.remove(i);

                    found = true;
                    foundKey = entry.getKey();
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        if (found) {

            // If that duration no longer has any shows, remove the key too
            if (TVList.get(foundKey).isEmpty()) {
                TVList.remove(foundKey);
            }

            String message = "Deleted item " + actualShowName + " from the map";

            System.out.println(message);
            out.println(message);
            out.flush();
        }
        // If the show was not found, display an error message
        else {
            String message = "Unable to delete " + showName
                    + ". Item is not in the map";

            System.out.println(message);
            out.println(message);
            out.flush();
        }
    }


    // PRE: none
    // POST: A quit message is displayed
    public static void quitProgram() {
        System.out.println("Quitting program...");
    }
}
