package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class PlaygroundClientProxy extends Thread implements RefereeCloning, CoachCloning, ContestantCloning {
    
    private static int nProxy = 0;
    private ServerCom sconi;
    private PlaygroundInterface playgroundInter;

    // Referee
    private RefereeStates refereeState;

    public void setRefereeState(RefereeStates state) {
        this.refereeState = state;
    }

    public RefereeStates getRefereeState() {
        return this.refereeState;
    }

    // Coach
    private CoachStates coachState;
    private int coachTeam;

    public void setCoachState(CoachStates state) {
        this.coachState = state;
    }

    public CoachStates getCoachState() {
        return this.coachState;
    }

    public void setCoachTeam(int team) {
        this.coachTeam = team;
    }

    public int getCoachTeam() {
        return this.coachTeam;
    }

    // Contestant
    private ContestantStates contestantState;
    private int contestantTeam;
    private int contestantNumber;
    private int contestantStrength;

    public void setContestantState(ContestantStates state) {
        this.contestantState = state;
    }

    public ContestantStates getContestantState() {
        return this.contestantState;
    }

    public void setContestantTeam(int team) {
        this.contestantTeam = team;
    }

    public int getContestantTeam() {
        return this.contestantTeam;
    }

    public void setContestantNumber(int number) {
        this.contestantNumber = number;
    }

    public int getContestantNumber() {
        return this.contestantNumber;
    }

    public void setContestantStrength(int strength) {
        this.contestantStrength = strength;
    }

    public int getContestantStrength() {
        return this.contestantStrength;
    }

    //
    
    public PlaygroundClientProxy(ServerCom sconi, PlaygroundInterface playgroundInter) {
        super("Playground_" + PlaygroundClientProxy.getProxyId());
        this.sconi = sconi;
        this.playgroundInter = playgroundInter;
    }

    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {cl = Class.forName("serverSide.entities.PlaygroundClientProxy");}
        catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type PlaygroundClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized(cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    @Override
    public void run() {
        Message inMessage = null, outMessage = null;

        inMessage = (Message)sconi.readObject();

        try {outMessage = playgroundInter.processAndReply(inMessage);}
        catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        sconi.writeObject(outMessage);
        sconi.close();
    }
}
