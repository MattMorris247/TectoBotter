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
    public static ArrayList<Member> OldMemberList = new ArrayList<Member>(); // DATA FROM THE PREVIOUS MONTH
    public static ArrayList<Member> NewMemberList = new ArrayList<Member>(); // DATA FROM NEW MONTH TO BE CROSS CHECKED NOT LITERAL NEW MEMBERS
                                                                    
    public static ArrayList<Member> notFoundList = new ArrayList<Member>(); // MEMBERS THAT HAVENT BEEN FOUND
    public static ArrayList<Member> actualNewMember = new ArrayList<Member>(); // ACTUALLY NEW MEMBERS
    public static JSONParser jsonParser = new JSONParser();
    public static int previousPlayerCount = 0 ; 
    public static int playerCount = 0 ; 
    
    public static void main(String[] args) throws Exception {
        // JSON parser object to parse read file
        System.out.println(PURP + "~~~STARTING~~~" + RESET);
        
        createLists(); 
        getData() ; 
        System.out.println(PURP + "==DEBUG INFO OVER===" + RESET);
        printPlayerCounts() ; 

        printNotFoundList(); 
        printNewMemberList(); 
       

        tests() ;
    
      
       
    }
    public static void tests() { 
        System.out.println("=====TESTING====") ; 
        int ehp = 0 ; 
        for ( int i = 0 ; i < NewMemberList.size(); i ++ ) { 
            ehp += NewMemberList.get(i).getDiff(); 
        }
        System.out.println(ehp) ; 





    }
    private static void createLists() { 
        createOldDataList();
        System.out.println(GREEN + "OldDataList " +  PURP+ "has been created..." + RESET) ;
        createNewDataList();
        System.out.println(GREEN + "NewDataList " +  PURP+ "has been created..." + RESET) ;
    }
    public static void printNotFoundList() { 
        int size =  notFoundList.size() ; 
        System.out.println("\nPlayers not Found ("+ RED + size+ RESET+ ") :" + RESET ) ; 
        System.out.print("["); 
        for (int i = 0 ; i < size ; i++ ) { 
            System.out.print(RED);
            System.out.print(notFoundList.get(i).getUser());
            if(i != size -1 ) 
            System.out.print(RESET + ", ") ; 
        }
        System.out.print(RESET + "]"); 
    }
    public static void printNewMemberList() { 
        int size = actualNewMember.size() ; 
        System.out.println("\n\nPlayers added ("+ GREEN + size+ RESET+ ") :" + RESET ) ; 
        System.out.print("["); 
        for (int i = 0 ; i < size ; i++ ) { 
            System.out.print(GREEN);
            System.out.print(actualNewMember.get(i).getUser());
            if(i != size -1 ) 
            System.out.print(RESET + ", ") ; 
        }
        System.out.print(RESET + "]"); 
        System.out.println(""); 
        System.out.println(""); 
    }
    public static void printPlayerCounts() { 
        System.out.println(BLUE  + "Previous Player Count" + RESET + " = " +YELLOW + previousPlayerCount + RESET) ; 
        if (playerCount<previousPlayerCount) { 
            System.out.println(BLUE + "Current  Player Count" + RESET + " = " + RED + playerCount+ RESET) ; 
        }
        else if(playerCount==previousPlayerCount){ 
            System.out.println(BLUE + "Current  Player Count" + RESET + " = " + YELLOW + playerCount +RESET ) ; 
        }
        else { 
            System.out.println(BLUE + "Current  Player Count" + RESET + " = " + GREEN + playerCount +RESET) ; 
        }
    }
    private static void getNewMembers() { 
        for ( int i = 0 ; i < NewMemberList.size(); i++) { 
            String currentUser= NewMemberList.get(i).getUser() ; 
            boolean newUser = true; 
            String updatedUser =""; 
            for ( int j = 0 ; j<OldMemberList.size(); j++ ) { 
                updatedUser = OldMemberList.get(j).getUser() ; 
                if(currentUser.equals(updatedUser))  
                    newUser= false; 
            }
            if(newUser) { 
                System.out.println(YELLOW + currentUser + GREEN + " is a new..." + RESET);
                actualNewMember.add(NewMemberList.get(i)); 
            } 
        }

    }
    public static void getData() {
        System.out.println(PURP + "Fetching Data..."); 
        for (int i = 0; i < OldMemberList.size(); i++) {
            String currentUser = OldMemberList.get(i).getUser();
            boolean found = false;
            String updatedUser = "";
            int diff ; 
            for (int j = 0; j < NewMemberList.size(); j++) {
                updatedUser = NewMemberList.get(j).getUser();
                if (currentUser.equals(updatedUser)) {
                    found = true;
                    diff = NewMemberList.get(j).getEhp() - OldMemberList.get(i).getEhp();
                    NewMemberList.get(j).setDiff(diff);
                }
            }
            if (!found) {
                System.out.println(YELLOW + currentUser + RED + " was not found..." + RESET);
                notFoundList.add(OldMemberList.get(i));
                
            }
      
                
        }
        getNewMembers();
    }
    public static void runTests() { 
    
        for (int i = 0; i < OldMemberList.size(); i++) {
            String currentUser = OldMemberList.get(i).getUser();
            boolean found = false;
            String newUser = "";
            int diff ; 
            for (int j = 0; j < NewMemberList.size(); j++) {
                newUser = NewMemberList.get(j).getUser();
                if (currentUser.equals(newUser)) {
                    found = true;
                 
                    diff = NewMemberList.get(j).getEhp() - OldMemberList.get(i).getEhp();
                    System.out.println(GREEN + "FOUND USER=" + BLUE + currentUser + PURP + " " + newUser + YELLOW
                            + " EHP Difference = " + GREEN + diff + RESET);
                    NewMemberList.get(j).setDiff(diff);
                }
            }
            if (!found) {
                System.out.println(YELLOW + currentUser + RED + " WAS NOT FOUND...added to NotFoundList" + RESET);
                notFoundList.add(OldMemberList.get(i));
                
            }
      
                
        }
    
    
    } 

    

    private static void createOldDataList() {
        try (FileReader reader = new FileReader("src/oldData.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray playerList = (JSONArray) obj;
            previousPlayerCount=playerList.size(); 
            for (int i = 0; i < playerList.size(); i++) {
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
                //System.out.println("Adding " + YELLOW + mem.getUser() + RESET + " " + i);
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
            playerCount=playerList.size(); 
            for (int i = 0; i < playerList.size(); i++) { 
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
                int ehpGain = Integer.parseInt(gained) ;
                Member mem = new Member(rsn, ehpGain, true);
                //System.out.println("Adding " + PURP + mem.getUser() + RESET + " " + i);
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
        int num = str.indexOf("."); 
        if (num != -1 ){ 
            String gained = str.substring(0, str.indexOf("."));
            return gained;
        }
       else{ 
        String noEHP= "0" ; 
        return noEHP; 
       }
    }
}
