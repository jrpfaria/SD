package serverSide.entities;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.sharedRegions.*;

/**
 * Service provider agent for access to the Contestants Bench.
 * <p>
 * Implementation of the client-server model type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ContestantsBenchClientProxy extends Thread implements RefereeCloning, CoachCloning, ContestantCloning {
    /**
     * Number of instances of the Contestants Bench client proxy.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private final ServerCom sconi;
    /**
     * ContestantsBench Interface.
     */
    private final ContestantsBenchInterface contestantsBenchInter;

    // Referee
    /**
     * Referee state.
     */
    private RefereeStates refereeState;
    // Coach
    /**
     * Coach state.
     */
    private CoachStates coachState;
    /**
     * Coach team.
     */
    private int coachTeam;
    // Contestant
    /**
     * Contestant state.
     */
    private ContestantStates contestantState;
    /**
     * Contestant team.
     */
    private int contestantTeam;
    /**
     * Contestant number.
     */
    private int contestantNumber;
    /**
     * Contestant strength.
     */
    private int contestantStrength;

    /**
     * Instantiation of the Contestants Bench client proxy.
     *
     * @param sconi                 communication channel
     * @param contestantsBenchInter Contestants Bench Interface
     */
    public ContestantsBenchClientProxy(ServerCom sconi, ContestantsBenchInterface contestantsBenchInter) {
        super("ContestantsBench_" + ContestantsBenchClientProxy.getProxyId());
        this.sconi = sconi;
        this.contestantsBenchInter = contestantsBenchInter;
    }

    /**
     * Generation of the instantiation id.
     *
     * @return instantiation id
     */
    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.ContestantsBenchClientProxy");
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type ContestantsBenchClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    /**
     * Referee state getter.
     *
     * @return referee state
     */
    public RefereeStates getRefereeState() {
        return this.refereeState;
    }

    /**
     * Referee state setter.
     *
     * @param state new referee state
     */
    public void setRefereeState(RefereeStates state) {
        this.refereeState = state;
    }

    /**
     * Coach state getter.
     *
     * @return state coach state
     */
    public CoachStates getCoachState() {
        return this.coachState;
    }

    /**
     * Coach state setter.
     *
     * @param state new coach state
     */
    public void setCoachState(CoachStates state) {
        this.coachState = state;
    }

    /**
     * Coach team getter.
     *
     * @return coach team number
     */
    public int getCoachTeam() {
        return this.coachTeam;
    }

    /**
     * Coach team setter.
     *
     * @param team new coach team number
     */
    public void setCoachTeam(int team) {
        this.coachTeam = team;
    }

    /**
     * Contestant state getter.
     *
     * @return contestant state
     */
    public ContestantStates getContestantState() {
        return this.contestantState;
    }

    /**
     * Contestant state setter.
     *
     * @param state new contestant state
     */
    public void setContestantState(ContestantStates state) {
        this.contestantState = state;
    }

    /**
     * Contestant team getter.
     *
     * @return contestant team number
     */
    public int getContestantTeam() {
        return this.contestantTeam;
    }

    /**
     * Contestant team setter.
     *
     * @param team new contestant team number
     */
    public void setContestantTeam(int team) {
        this.contestantTeam = team;
    }

    /**
     * Contestant number getter.
     *
     * @return contestant number
     */
    public int getContestantNumber() {
        return this.contestantNumber;
    }

    /**
     * Contestant number setter.
     *
     * @param number new contestant number
     */
    public void setContestantNumber(int number) {
        this.contestantNumber = number;
    }

    //

    /**
     * Contestant strength getter.
     *
     * @return contestant strength
     */
    public int getContestantStrength() {
        return this.contestantStrength;
    }

    /**
     * Contestant strength setter.
     *
     * @param strength new contestant strength
     */
    public void setContestantStrength(int strength) {
        this.contestantStrength = strength;
    }

    /**
     * Life cycle of the service provider agent.
     */
    @Override
    public void run() {
        Message inMessage, outMessage = null;

        inMessage = (Message) sconi.readObject();

        try {
            GenericIO.writelnString(getName() + " received " + inMessage.getMsgType() + "!");
            outMessage = contestantsBenchInter.processAndReply(inMessage);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        GenericIO.writelnString(getName() + " sent " + outMessage.getMsgType() + "!");

        sconi.writeObject(outMessage);
        //sconi.close();
    }
}
