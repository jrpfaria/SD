package clientSide.entities;

public interface CoachCloning {
    public void setCoachState(CoachStates state);
    public CoachStates getCoachState();
    public void setCoachTeam(int team);
    public int getCoachTeam();
}
