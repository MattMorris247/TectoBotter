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
    public static final String GREEN = "\u001B[32m";
    public static final String PURP = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static ArrayList<Member> OldMemberList = new ArrayList<Member>();
    public static ArrayList<Member> NewMemberList = new ArrayList<Member>();
    public static ArrayList<Member> notFoundList = new ArrayList<Member>();
    public static JSONParser jsonParser = new JSONParser();

    public static void main(String[] args) throws Exception {
        // JSON parser object to parse read file
        System.out.println("STARTING");
        createOldDataList();
        createNewDataList();

        System.out.println(OldMemberList);
        System.out.println(NewMemberList);

        System.out.println(notFoundList);

        System.out.println("TESTING COMPARISONS");

        for (int i = 0; i < OldMemberList.size(); i++) {
            String currentUser = OldMemberList.get(i).getUser();
            boolean found = false;
            boolean newMember = true;
            int index;
            String newUser = "";
            for (int j = 0; j < NewMemberList.size(); j++) {
                newUser = NewMemberList.get(j).getUser();

                if (currentUser.equals(newUser)) {
                    found = true;
                    newMember = false;
                    int diff = NewMemberList.get(j).getEhp() - OldMemberList.get(i).getEhp();
                    System.out.println(GREEN + "FOUND USER=" + BLUE + currentUser + PURP + " " + newUser + YELLOW
                            + " EHP Difference = " + GREEN + diff + RESET);
                }

            }
            if (!found) {
                System.out.println(YELLOW + currentUser + RED + " WAS NOT FOUND...added to NotFoundList" + RESET);
                notFoundList.add(OldMemberList.get(i));
            }
            if (newMember)
                System.out.println(YELLOW + newUser + GREEN + " is a new Member...added to NewMemberList");
        }
    }

    private static void createOldDataList() {
        try (FileReader reader = new FileReader("src/oldData.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray playerList = (JSONArray) obj;
            int NUM = 10; // SET THIS TO PLAYER SIZE EVENTUALLY
            for (int i = 0; i < NUM; i++) {
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
                System.out.println("Adding " + YELLOW + mem.getUser() + RESET + " " + i);
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
        try (FileReader reader = new FileReader("src/newData.json")) { // Replace w NewData.json

            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray playerList = (JSONArray) obj; // PlayerList size = 198
            int NUM = 10; // SET THIS TO PLAYER SIZE EVENTUALLY
            for (int i = 0; i < NUM; i++) { // IF NUM IS GREATER THAN 170 , IT BREAKS ?????
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
                System.out.println("Adding " + PURP + mem.getUser() + RESET + " " + i);
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
