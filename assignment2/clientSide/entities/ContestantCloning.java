package clientSide.entities;

public interface ContestantCloning {
    public void setContestantState(ContestantStates state);
    public ContestantStates getContestantState();
    public void setContestantTeam(int team);
    public int getContestantTeam();
    public void setContestantStrength(int strength);
    public int getContestantStrength();
}
