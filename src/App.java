import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;

public class App {
    @SuppressWarnings("unchecked")
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\u001B[33m";

    public static ArrayList<Member> OldMemberList = new ArrayList<Member>();
    public static ArrayList<Member> NewMemberList = new ArrayList<Member>();
    public static JSONParser jsonParser = new JSONParser();

    public static void main(String[] args) throws Exception {
        // JSON parser object to parse read file
        createOldDataList();
        createNewDataList();

        System.out.println(OldMemberList);
        System.out.println(NewMemberList);

    }

    private static void createOldDataList() {
        try (FileReader reader = new FileReader("src/OldData.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray playerList = (JSONArray) obj;
            for (int i = 0; i < 5; i++) {
                // Parsing RSN
                String player = playerList.get(i).toString();
                int rsnIndex = player.indexOf("username") + 11;
                String str = player.substring(rsnIndex, player.length());
                String rsn = rsnTrimmer(str);
                // PARSING EXP GAINED (END DATE)
                int newIndex = player.indexOf("gained") + 8;
                int startIndex = player.indexOf("start");
                String str2 = player.substring(newIndex, startIndex);
                String gained = gainTrimmer(str2);
                int ehpGain = Integer.parseInt(gained);
                Member mem = new Member(rsn, ehpGain, false);
                OldMemberList.add(mem);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void createNewDataList() {
        try (FileReader reader = new FileReader("src/OldData.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray playerList = (JSONArray) obj;
            for (int i = 0; i < 5; i++) {
                // Parsing RSN
                String player = playerList.get(i).toString();
                int rsnIndex = player.indexOf("username") + 11;
                String str = player.substring(rsnIndex, player.length());
                String rsn = rsnTrimmer(str);
                // PARSING EXP GAINED (END DATE)
                int newIndex = player.indexOf("gained") + 8;
                int startIndex = player.indexOf("start");
                String str2 = player.substring(newIndex, startIndex);
                String gained = gainTrimmer(str2);
                int ehpGain = Integer.parseInt(gained) + 5;
                Member mem = new Member(rsn, ehpGain, true);
                NewMemberList.add(mem);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static String rsnTrimmer(String str) {
        String rsn = str.substring(0, str.length() - 2);
        return rsn;
    }

    private static String gainTrimmer(String str) {

        String gained = str.substring(0, str.indexOf("."));
        return gained;
    }
}
