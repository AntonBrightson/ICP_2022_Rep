import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.util.Scanner;

public class Flight_Routes{
    public static void main(String[] args) throws IOException, CsvException{
    
    // Routes
    CSVReader route = new CSVReader(new FileReader(new File("C:\\Users\\PC\\Desktop\\ICP_Proj1\\flight_info\\routes.csv")));
    List<String[]> route_list = route.readAll();

    String[][] data_route = new String[route_list.size()][];
    data_route = route_list.toArray(data_route);
    //System.out.println(data_route[1][3]);

    // Airports
    CSVReader airP = new CSVReader(new FileReader(new File("C:\\Users\\PC\\Desktop\\ICP_Proj1\\flight_info\\airports.csv")));
    List<String[]> airP_list = airP.readAll();

    String[][] data_airP = new String[airP_list.size()][];
    data_airP = airP_list.toArray(data_airP);

    // Airlines
    CSVReader airL = new CSVReader(new FileReader(new File("C:\\Users\\PC\\Desktop\\ICP_Proj1\\flight_info\\airlines.csv")));
    List<String[]> list = airL.readAll();

    String[][] data_airL = new String[list.size()][];
    data_airL = list.toArray(data_airL);
    

    //Taking user input of start location and end location
    // from input file
    List<String> begin_end = new ArrayList<String>();
    Scanner start_end = new Scanner(
        new FileReader("C:\\Users\\PC\\Desktop\\ICP_Proj1\\inputFILE.txt")).useDelimiter(",\\s*"); 
    // Delimiter splits text in input file into two indiv strings
    String read;
    while (start_end.hasNextLine()){  //checks if input file contains information and stores into array
        read = start_end.next();
        begin_end.add(read);
    }    
    String[] startL_endL = begin_end.toArray(new String[0]);

    //Routing
    int count = 0;
    int count2 = 0;
    List<Integer> count3 = new ArrayList<Integer>(); //stores the multiple starting locations
   
    for (int i = 0; ;){     //find begining location for the begining/location airport
        if (data_airP[i][2].equals(startL_endL[0])){
            count = i;
            break;
        }
        else{
            i++;
        }
    }
    for (int i = 0; ;){       //find destination location for destination airport
        if (data_airP[i][2].equals(startL_endL[0])){
            count2 = i;
            break;
        }
        else{
            i++;
        }
    }
    String start_airP = data_airP[count][0]; //stores location airport ID
    String end_airP= data_airP[count2][0];   //stores destination airport ID

    for (int i = 0; ;){               //takes multiple start locations and compares to location airport IDs
        if (data_route[i][3].equals(start_airP)){
            count3.add(i);
            start_airP = data_route[i][5];
            i = 0;
        }
        if (start_airP.equals(end_airP)){  //ensures the end location is the wanted end destination
            break;
        }
        else{
            i++;
        }
    }
    //Writing the routes into a .txt file
    int stops = 0;
    int flights_num = 0;
    File path_File = new File("path_Out.txt");
    FileOutputStream path_Out = new FileOutputStream(path_File);
    BufferedWriter bread = new BufferedWriter(new OutputStreamWriter(path_Out));

    if (path_File.length() != 0){
        new FileOutputStream(path_File).close();
    }
    else{
        for (int i=0; i<count3.size(); i++){
            bread.write(data_route[count3.get(i)][0]+ " from "+ data_route[count3.get(i)][2]+ " to "+data_route[count3.get(i)][4]+" "
            +data_route[count3.get(i)][7]+" stops ");
            flights_num = i;
            stops += Integer.parseInt(data_route[count3.get(i)][7]);
            bread.newLine();
        }
    }
    bread.write("Total flights: "+flights_num+1);
    bread.newLine();
    bread.write("Total additional flights: "+stops);
    bread.close();
 }
}

