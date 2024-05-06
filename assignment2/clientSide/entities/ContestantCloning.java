package clientSide.entities;

public interface ContestantCloning {
    ContestantStates getContestantState();

    void setContestantState(ContestantStates state);

    int getContestantTeam();

    void setContestantTeam(int team);

    int getContestantStrength();

    void setContestantStrength(int strength);
}
