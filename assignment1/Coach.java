package assignment1;

public class Coach {
    private Player[] team;

    public Coach(Player[] team){
        this.team = team;
    }

    public Player[] selectPlayers(){
        Player[] sorted = new Player[team.length];

        for (int i = 0; i < team.length; i++){
            sorted[i] = team[i];
        }

        for (int i = 0; i < sorted.length; i++){
            for (int j = i + 1; j < sorted.length; j++){
                if (sorted[i].getStrength() < sorted[j].getStrength()){
                    Player temp = sorted[i];
                    sorted[i] = sorted[j];
                    sorted[j] = temp;
                }
            }
        }

        return new Player[]{sorted[0], sorted[1], sorted[2]};
    }
}
