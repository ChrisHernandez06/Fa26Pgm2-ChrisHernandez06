import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        System.out.println("Welcome to Program 2: Maps");
        Map<Integer, ArrayList<String>> TVList = new HashMap<>();

        Scanner input = new Scanner(System.in);

        //open output file
        try{
            PrintWriter out = new PrintWriter("report.txt");
            
            //load data into map
            Functions.loadData( TVList);

            //sort the map
            Map<Integer, ArrayList<String>> sortedTVList = new TreeMap<>(TVList);

            String menuItem = Functions.getMenuItem(input);

            while (!menuItem.equals("Q")) {

                // Menu selection handling
                switch (menuItem) {

                    case "A":
                        Functions.addShow(sortedTVList, input, out);
                        break;

                    case "D":
                        Functions.deleteShow(sortedTVList, input, out);
                        break;

                    case "K":
                        Functions.printKeys(sortedTVList, out);
                        break;

                    case "P":
                        Functions.printMap(sortedTVList, out);
                        break;

                    case "S":
                        Functions.printSpecificKey(sortedTVList, input, out);
                        break;
                }

                menuItem = Functions.getMenuItem(input);
            }

            // Once loop exits, quit the program
            Functions.quitProgram();

            //close files
            input.close();
            out.close();

        }
        catch (Exception e){
            System.out.println("Error in input record");
            return;
        }
    }

}
