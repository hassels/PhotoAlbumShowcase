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
    public static void main(String args[]) throws Exception {
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
    public static void getRequest(int albumNumber) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        String newTitle;
        String newId;
        BufferedReader reader = createConnection(albumNumber);
        StringBuffer result = new StringBuffer();
        // while there is still information in the url read
        // it in. If it is an id or title place it into
        // the desired results
        String readLine = null;
        while ((readLine = reader.readLine()) != null) {
            if (readLine.length() > 10) {
                if (readLine.substring(0, 9).equals("    \"id\":")) {
                    newId = "";
                    String formattedString = readLine.replaceAll("[, ]", "");
                    String[] splitLine = (formattedString.split(":"));
                    newId = splitLine[1];
                }
            }

            if (isTitle(readLine)) {
                    newTitle = "";
                    String formattedString = readLine.replaceAll("[,\"]", "");
                    String[] splitLine = (formattedString.split(":"));
                    newTitle = splitLine[1];
                    resultMap.put(newId, newTitle);
            }
        }
        reader.close();

        //print out the photo ids and titles obtained from the url
        for (String name : resultMap.keySet()) {
            String key = name;
            String value = resultMap.get(name);
            System.out.println(key + " " + value);
        }
    }


    public static BufferedReader createConnection(int albumNumber) throws Exception {
        // establish connection with url
        String urlString = "https://jsonplaceholder.typicode.com/photos?albumId=" + Integer.toString(albumNumber);
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // establish type of request with connection
        connection.setRequestMethod("GET");
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));


    }

    public static boolean isTitle(String readLine){
        return readLine.length() > 11 && readLine.substring(0, 12).equals("    \"title\":");
    }
}
