import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.io.FileNotFoundException;

import java.io.IOException;
  
public class App {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception { 
          //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/OldData.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray playerList = (JSONArray) obj;
            System.out.println(playerList);
             
            //Iterate over employee array
            playerList.forEach( play -> parsePlayerObject( (JSONObject) play ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static void parsePlayerObject(JSONObject player) 
    {
        //Get employee object within list
        JSONObject playerObject = (JSONObject) player.get("player");
         
        //Get employee first name
        String rsn= (String) playerObject.get("name");    
        System.out.println(rsn);
        
    }
}
        


         
   