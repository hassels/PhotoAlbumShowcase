import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/*
This class returns to the console the photo id and titles of
each photo in the specified album.
 */
public class Showcase {
    public static void main(String args[]) throws IOException {
        int albumNumber;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a photo album number");
        albumNumber = reader.nextInt();
        reader.close();
        getRequest(albumNumber);
    }

    // getRequest method creates a connection with the url that
    // the requested album is located at and gets the photo
    // information from it.
    public static void getRequest(int albumNumber) throws IOException {
        // establish connection with url
        String urlString = "https://jsonplaceholder.typicode.com/photos?albumId=" + Integer.toString(albumNumber);
        URL url = new URL(urlString);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // establish type of request with connection
        connection.setRequestMethod("GET");
        // check if connection is OK
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String newTitle="";
            String newId="";
            HashMap<String,String> resultMap = new HashMap<>();
            // create buffer to read in the information from the url
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer result  = new StringBuffer();
            // while there is still information in the url read
            // it in. If it is an id or title place it into
            // the desired results
            while ((readLine = reader.readLine()) != null) {
              if(readLine.length() > 10){
                  if(readLine.substring(0,9).equals("    \"id\":")){
                      newId = "";
                      String formattedString = readLine.replaceAll("[, ]","");
                      String[] splitLine = (formattedString.split(":"));
                      newId = splitLine[1];
                  }
              }
              if(readLine.length()>11) {
                  if (readLine.substring(0, 12).equals("    \"title\":")) {
                      newTitle = "";
                      String formattedString = readLine.replaceAll("[, \"]", "");
                      String[] splitLine = (formattedString.split(":"));
                      newTitle = splitLine[1];
                      resultMap.put(newId,newTitle);
                  }
              }
            }
            reader.close();

            //print out the photo ids and titles obtained from the url
            for (String name: resultMap.keySet()){
                String key =name.toString();
                String value = resultMap.get(name).toString();
                System.out.println(key + " " + value);
            }
        }
        else {
            System.out.println("Status Code Not OK");
        }
    }
}
