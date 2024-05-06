package clientSide.entities;

public interface CoachCloning {
    CoachStates getCoachState();

    void setCoachState(CoachStates state);

    int getCoachTeam();

    void setCoachTeam(int team);
}
