import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    Player playerObject = new Player();
    static Player team_1_Players[] = new Player[11];
    static Player team_2_Players[] = new Player[11];
    static Player batting_team[] = new Player[11];
    static Player bowling_team[] = new Player[11];

    static Player currentPlayer, oppPlayer, current_bowler;

    static ArrayList<String> over1 = new ArrayList<>();
    static ArrayList<ArrayList<String>> overs1 = new ArrayList<>();

    static ArrayList<String> over2 = new ArrayList<>();
    static ArrayList<ArrayList<String>> overs2 = new ArrayList<>();

    static int team_1_total = 0;
    static int team_2_total = 0;
    static int team_1_wickets = 0;
    static int team_2_wickets = 0;
    static int team_1_extras = 0;
    static int team_2_extras = 0;

    private static Player findPlayer(Player playersList[], String name) {
        for (Player player : playersList) {
            if (player.get_name().equals(name)) {
                return player;
            }
        }
        return new Player();
    }

    public static boolean containsPlayer(Player playersList[], String name) {
        for (Player player : playersList) {
            if (player.get_name().equals(name))
                return true;
        }
        return false;
    }

    public static Player set_bowler(Player playersList[], String bowler) {
        boolean status = true;
        Player name = null;
        while (status) {
            if (containsPlayer(bowling_team, bowler)) {
                name = findPlayer(bowling_team, bowler);
                status = false;
            } else {
                System.out.print("invalid player name");
            }
        }
        return name;
    }

    public static void changeStrike() {
        Player temp = currentPlayer;
        currentPlayer = oppPlayer;
        oppPlayer = temp;
    }

    public static Player strike(Player batting_team[], String player) {
        boolean status = true;
        Player name = null;
        while (status) {
            if (containsPlayer(batting_team, player)) {
                name = findPlayer(batting_team, player);
                status = false;
            } else {
                System.out.print("invalid player name");
            }
        }
        return name;
    }

    public static Player non_strike(Player playersList[], String non_strike) {
        boolean status = true;
        Player name = null;
        while (status) {
            if (containsPlayer(batting_team, non_strike)) {
                name = findPlayer(batting_team, non_strike);
                status = false;
            } else {
                System.out.print("invalid player name");
            }
        }
        return name;
    }

    public static void innings(int team) {
        if (team == 1) {
            for (int i = 0; i < 11; i++) {
                batting_team[i] = team_1_Players[i];
                bowling_team[i] = team_2_Players[i];
            }
        } else {
            for (int i = 0; i < 11; i++) {
                batting_team[i] = team_2_Players[i];
                bowling_team[i] = team_1_Players[i];
            }
        }
    }

    public static void apply_changes(int team) {
        if (team == 1) {
            for (int i = 0; i < 11; i++) {
                team_1_Players[i] = batting_team[i];
                team_2_Players[i] = bowling_team[i];
            }
        } else {
            for (int i = 0; i < 11; i++) {
                team_2_Players[i] = batting_team[i];
                team_1_Players[i] = bowling_team[i];
            }
        }
    } 

    // public static boolean isValidBall(String option) {
    // if(option.equals("NB") || option.equals("WD")) return false;
    // return true;
    // }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        team_1_Players[0] = new Player("t1 player 1");
        team_1_Players[1] = new Player("t1 player 2");
        team_1_Players[2] = new Player("t1 player 3");
        team_1_Players[3] = new Player("t1 player 4");
        team_1_Players[4] = new Player("t1 player 5");
        team_1_Players[5] = new Player("t1 player 6");
        team_1_Players[6] = new Player("t1 player 7");
        team_1_Players[7] = new Player("t1 player 8");
        team_1_Players[8] = new Player("t1 player 9");
        team_1_Players[9] = new Player("t1 player 10");
        team_1_Players[10] = new Player("t1 player 11");

        team_2_Players[0] = new Player("t2 player 1");
        team_2_Players[1] = new Player("t2 player 2");
        team_2_Players[2] = new Player("t2 player 3");
        team_2_Players[3] = new Player("t2 player 4");
        team_2_Players[4] = new Player("t2 player 5");
        team_2_Players[5] = new Player("t2 player 6");
        team_2_Players[6] = new Player("t2 player 7");
        team_2_Players[7] = new Player("t2 player 8");
        team_2_Players[8] = new Player("t2 player 9");
        team_2_Players[9] = new Player("t2 player 10");
        team_2_Players[10] = new Player("t2 player 11");

        System.out.print("First batting (1/2) : ");
        int team = scan.nextInt();
        scan.nextLine(); 

        System.out.print("Strike : ");
        String strike = scan.nextLine();
        System.out.print("Non Strike : ");
        String non_strike = scan.nextLine();

        currentPlayer = strike(batting_team, strike);
        oppPlayer = non_strike(batting_team, non_strike);

        System.out.print("Bowler : ");
        String bowler = scan.nextLine();
        current_bowler = set_bowler(bowling_team, bowler);

        int wickets = 0;
        int ball_count = 0;

        innings(team);
        while (overs1.size() != 20 && wickets != 10) {
            if (ball_count == 6) {
                ball_count = 0;
                overs1.add(over1);
                over1.clear();
                changeStrike();
                System.out.print("Bowler : ");
                bowler = scan.nextLine();
                current_bowler = set_bowler(bowling_team, bowler);
            }
            System.out.println("0 1 2 3 4 5 6 W WD LB NB");
            String choice = scan.next();
            if (choice.matches("[0-6]")) {
                over1.add(choice);
                ball_count++;
                currentPlayer.set_runs(currentPlayer.get_runs() + Integer.valueOf(choice));
                team_1_total += Integer.valueOf(choice);
                if (Integer.valueOf(choice) % 2 == 1) {
                    changeStrike();
                }
            } else {
                System.out.print("Extras : ");
                int additional = scan.nextInt();
                scan.nextLine();
                team_1_extras += additional;
                over1.add(choice);
                if (choice.equals("W")) {
                    wickets++;
                    ball_count++;
                    if (wickets < 10) {
                        System.out.print("in at " + (wickets + 2) + " : ");
                        strike = scan.nextLine();
                        currentPlayer = strike(batting_team, strike);
                    } else {
                        System.out.println(team_1_total + "/" + wickets + "    " + "Target : " + (team_1_total + 1));
                    }
                } else if (choice.equals("NB") || choice.equals("WD")) {
                    team_1_extras++;
                }
            }
            System.out.println("Total : "+team_1_total);
            System.out.println(currentPlayer.get_name()+ " "+currentPlayer.get_runs());
            System.out.println(current_bowler.get_name());
        }
        apply_changes(team);
        if(team == 1) 
            team = 2;
        else 
            team = 1;
        innings(team);
        System.out.print("Strike : ");
        strike = scan.nextLine();
        System.out.print("Non Strike : ");
        non_strike = scan.nextLine();

        currentPlayer = strike(batting_team, strike);
        oppPlayer = non_strike(batting_team, non_strike);

        System.out.print("Bowler : ");
        bowler = scan.nextLine();
        current_bowler = set_bowler(bowling_team, bowler);

        wickets = 0;
        ball_count = 0;
        while (overs2.size() != 20 && wickets != 10 && team_2_total <= team_1_total) {
            if (ball_count == 6) {
                ball_count = 0;
                overs2.add(over2);
                over2.clear();
                changeStrike();
                System.out.print("Bowler : ");
                bowler = scan.nextLine();
                current_bowler = set_bowler(bowling_team, bowler);
            }
            System.out.println("0 1 2 3 4 5 6 W WD LB NB");
            String choice = scan.next();
            if (choice.matches("[0-6]")) {
                over2.add(choice);
                ball_count++;
                currentPlayer.set_runs(currentPlayer.get_runs() + Integer.valueOf(choice));
                team_2_total += Integer.valueOf(choice);
                if (Integer.valueOf(choice) % 2 == 1) {
                    changeStrike();
                }
            } else {
                System.out.print("Extras : ");
                int additional = scan.nextInt();
                scan.nextLine();
                team_2_extras += additional;
                over2.add(choice);
                if (choice.equals("W")) {
                    wickets++;
                    ball_count++;
                    if (wickets < 10) {
                        System.out.print("in at " + (wickets + 2) + " : ");
                        strike = scan.nextLine();
                        currentPlayer = strike(batting_team, strike);
                    } else {
                        System.out.println(team_2_total + "/" + wickets + "    " + "Target : " + (team_1_total + 1));
                    }
                } else if (choice.equals("NB") || choice.equals("WD")) {
                    team_2_extras++;
                }
            }
            if(team_2_total > team_1_total) 
                System.out.print("Team 2 win by "+(10 - wickets)+" wickets");
        }
        apply_changes(team);
    }
}

class Player {
    private String name;
    private int runs = 0;
    private int balls = 0;
    private int overs = 0;
    private int wickets = 0;
    private int six = 0, four = 0, two = 0, one = 0;
    private String wicketBy;
    private String wicketType;

    Player() {
    }

    Player(String name) {
        this.name = name;
    }

    public String get_name() {
        return this.name;
    }

    public int get_runs() {
        return this.runs;
    }

    public void set_runs(int runs) {
        this.runs += runs;
    }

    public void set_balls() {
        this.balls++;
    }

    public void set_overs() {
        this.overs++;
    }

    public void set_wickets() {
        this.wickets++;
    }

    public void set_one() {
        this.one++;
    }

    public void set_two() {
        this.two++;
    }

    public void set_four() {
        this.four++;
    }

    public void set_six() {
        this.six++;
    }

    public void set_wicketBy_and_type(String name, String type) {
        this.wicketBy = name;
        this.wicketType = type;
    }
}