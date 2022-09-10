public class Member {
    private String rsn;
    private int ehp;
    private boolean type;
    private int ehpDifference; 
    private int stageIndex; 
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURP = "\u001B[35m";

    public Member(String username, int gained, boolean typer) {
        rsn = username;
        ehp = gained;
        type = typer; // OLD = FALSE , NEW = TRUE  
        ehpDifference=  0 ;  //is set during runData()  (NOT CONSTRUCTOR) 
        stageIndex = getStage(); 
    }
    private int getStage() { 
        if (ehp < 10)
        return 0 ; 
        if (ehp < 25)
        return 1; 
        if (ehp < 50)
        return 2;
        if (ehp < 75)
        return 3;
        if (ehp <100) 
        return 4; 
        if (ehp <125) 
        return 5;
        if (ehp <150)
        return 6;
        if(ehp <175) 
        return 7;
        if (ehp <200 ) 
        return 8;
        if (ehp < 225) 
        return 9 ;
        if (ehp < 250) 
        return 10;
        if (ehp < 275) 
        return 11;
        if (ehp < 300)
        return 12;
        if (ehp <325) 
        return 13;
        if (ehp <350) 
        return 14;
        if (ehp <375)
        return 15;
        if (ehp <400)
        return 16;
        if (ehp <425) 
        return 17;
        if (ehp <450)
        return 18; 
        if (ehp <475)
        return 19; 
        if (ehp <500) 
        return 20; 

        else
        return 0 ;
    }

    public String toString() {
        if (!type) {
            return (BLUE + rsn + RESET + " " + YELLOW + ehp + RESET);
        } else {
            return (PURP + rsn + RESET + " " + YELLOW + ehp + RESET);
        }
    }
    public void setDiff(int num ) { 
        ehpDifference=num ; 
    }
    public String getUser() {
        return this.rsn;
    }
    public int getDiff() {
        return this.ehpDifference;
    }

    public int getEhp() {
        return this.ehp;
    }

    public int compare(Member mem1, Member mem2) {
        String user1 = mem1.getUser();
        String user2 = mem2.getUser();

        if (user1.equals(user2)) {
            return 0;
        }
        if (user1.compareTo(user2) < 0) {
            return -1;
        } else {
            return 1;
        }

    }

}
