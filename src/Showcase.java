import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Showcase {
    public static void main(String args[]) throws IOException {
        int albumNumber;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a photo album number");
        albumNumber = reader.nextInt();
        reader.close();
        getRequest(albumNumber);
    }

    public static void getRequest(int albumNumber) throws IOException {
        String urlString = "https://jsonplaceholder.typicode.com/photos?albumId=" + Integer.toString(albumNumber);
        URL url = new URL(urlString);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String newTitle="";
            String newId="";
            HashMap<String,String> resultMap = new HashMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer result  = new StringBuffer();
            while ((readLine = reader.readLine()) != null) {
                result.append(readLine);
              if(readLine.length() > 10){
                  if(readLine.substring(0,9).equals("    \"id\":")){
                      newId = "";
                      String formattedString = readLine.replaceAll("[, ]","");
                      String[] splitLine = (formattedString.split(":"));
                      newId = splitLine[1];
                      System.out.println(newId);
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
