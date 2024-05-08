package serverSide.entities;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.sharedRegions.*;

/**
 * Service provider agent for access to the Referee Site.
 * <p>
 * Implementation of the client-server model type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class RefereeSiteClientProxy extends Thread implements RefereeCloning, CoachCloning {
    /**
     * Number of instances of the Referee Site client proxy.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private final ServerCom sconi;
    /**
     * Referee Site Interface.
     */
    private final RefereeSiteInterface refereeSiteInter;

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
     * Coach team number.
     */
    private int coachTeam;

    /**
     * Instantiation of the Referee Site client proxy.
     *
     * @param sconi            Communication channel
     * @param refereeSiteInter Referee Site Interface
     */
    public RefereeSiteClientProxy(ServerCom sconi, RefereeSiteInterface refereeSiteInter) {
        super("RefereeSite_" + RefereeSiteClientProxy.getProxyId());
        this.sconi = sconi;
        this.refereeSiteInter = refereeSiteInter;
    }

    /**
     * Generation of the instantiation id.
     *
     * @return Instantiation id
     */
    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.RefereeSiteClientProxy");
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type RefereeSiteClientProxy was not found!");
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
     * @return State of the referee
     */
    public RefereeStates getRefereeState() {
        return this.refereeState;
    }

    /**
     * Referee state setter.
     *
     * @param state New state of the referee
     */
    public void setRefereeState(RefereeStates state) {
        this.refereeState = state;
    }

    /**
     * Coach state getter.
     *
     * @return State of the coach
     */
    public CoachStates getCoachState() {
        return this.coachState;
    }

    /**
     * Coach state setter.
     *
     * @param state New state of the coach
     */
    public void setCoachState(CoachStates state) {
        this.coachState = state;
    }

    //

    /**
     * Coach team getter.
     *
     * @return Coach team number
     */
    public int getCoachTeam() {
        return this.coachTeam;
    }

    /**
     * Coach team setter.
     *
     * @param team New coach team number
     */
    public void setCoachTeam(int team) {
        this.coachTeam = team;
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
            outMessage = refereeSiteInter.processAndReply(inMessage);
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
