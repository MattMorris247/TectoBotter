public class Member {
    private String rsn;
    private int ehp;
    private boolean type;
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURP = "\u001B[35m";

    public Member(String username, int gained, boolean typer) {
        rsn = username;
        ehp = gained;
        type = typer;
    }

    public String toString() {
        if (!type) {
            return (BLUE + rsn + RESET + " " + YELLOW + ehp + RESET);
        } else {
            return (PURP + rsn + RESET + " " + YELLOW + ehp + RESET);
        }
    }

    public String getUser() {
        return this.rsn;
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