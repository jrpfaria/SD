package commInfra;

import clientSide.entities.*;
import serverSide.main.SimulPar;

import java.io.Serializable;

/**
 * Internal structure of the exchanged messages.
 * <p>
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class Message implements Serializable {
    /**
     * Serial version of the class.
     */
    private static final long serialVersionUID = 2021L;

    /**
     * Type of the message.
     */
    private MessageType msgType;
    /**
     * State of the referee.
     */
    private RefereeStates refereeState = null;
    /**
     * State of the coach.
     */
    private CoachStates coachState = null;
    /**
     * State of the contestant.
     */
    private ContestantStates contestantState = null;
    /**
     * Team number.
     */
    private int team = -1;
    /**
     * Contestant number.
     */
    private int number = -1;
    /**
     * Strength of the contestant.
     */
    private int strength = -1;
    /**
     * Value of the message.
     */
    private int value = -1;
    /**
     * Contestants in the game.
     */
    private Pair<Integer, Integer>[] contestants;
    /**
     * Roster of the team.
     */
    private int[] roster;
    /**
     * Strength of all the contestants.
     */
    private int[][] contestantStrength;
    /**
     * Name of the log file.
     */
    private String logFileName = null;
    /**
     * Position of the rope.
     */
    private int position = 0;
    /**
     * Knockout flag.
     */
    private boolean knockout = false;
    /**
     * Score of team 1.
     */
    private int score1 = -1;
    /**
     * Score of team 2.
     */
    private int score2 = -1;

    /**
     * Message instantiation.
     *
     * @param msgType Type of the message
     */
    public Message(MessageType msgType) {
        this.msgType = msgType;
    }

    /**
     * Message toString.
     *
     * @return String representation of the message
     */
    @Override
    public String toString() {
        return this.msgType.toString() + " team:" + team + " number:" + number;
    }

    /**
     * MsgType getter
     *
     * @return Type of the message
     */
    public MessageType getMsgType() {
        return this.msgType;
    }

    /**
     * MsgType setter
     *
     * @param msgType New message type
     * @return reference to this object
     */
    public Message setMsgType(MessageType msgType) {
        this.msgType = msgType;
        return this;
    }

    /**
     * RefereeState getter
     *
     * @return State of the referee
     */
    public RefereeStates getRefereeState() {
        return refereeState;
    }

    /**
     * RefereeState setter
     *
     * @param refereeState New state of the referee
     * @return reference to this object
     */
    public Message setRefereeState(RefereeStates refereeState) {
        this.refereeState = refereeState;
        return this;
    }

    /**
     * CoachState getter
     *
     * @return State of the coach
     */
    public CoachStates getCoachState() {
        return coachState;
    }

    /**
     * CoachState setter
     *
     * @param coachState New state of the coach
     * @return reference to this object
     */
    public Message setCoachState(CoachStates coachState) {
        this.coachState = coachState;
        return this;
    }

    /**
     * ContestantState getter
     *
     * @return State of the contestant
     */
    public ContestantStates getContestantState() {
        return contestantState;
    }

    /**
     * ContestantState setter
     *
     * @param contestantState New state of the contestant
     * @return reference to this object
     */
    public Message setContestantState(ContestantStates contestantState) {
        this.contestantState = contestantState;
        return this;
    }

    /**
     * Team getter
     *
     * @return Team number
     */
    public int getTeam() {
        return team;
    }

    /**
     * Team setter
     *
     * @param team New team number
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setTeam(int team) throws MessageException {
        if (team != 0 && team != 1)
            throw new MessageException("Invalid team number: " + team, this);
        this.team = team;
        return this;
    }

    /**
     * Number getter
     *
     * @return Contestant number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Number setter
     *
     * @param number New contestant number
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setNumber(int number) throws MessageException {
        if (number < 0 || number >= SimulPar.NC)
            throw new MessageException("Invalid number: " + number, this);
        this.number = number;
        return this;
    }

    /**
     * Strength getter
     *
     * @return Strength of the contestant
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Strength setter
     *
     * @param strength New strength of the contestant
     * @return reference to this object
     */
    public Message setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    /**
     * Value getter
     *
     * @return Value of the message
     */
    public int getValue() {
        return value;
    }

    /**
     * Value setter
     *
     * @param value New value of the message
     * @return reference to this object
     */
    public Message setValue(int value) {
        this.value = value;
        return this;
    }

    /**
     * Contestants getter
     *
     * @return Contestants in the game
     */
    public Pair<Integer, Integer>[] getContestants() {
        return contestants;
    }

    /**
     * Contestants setter
     *
     * @param contestants New contestants in the game
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setContestants(Pair<Integer, Integer>[] contestants) throws MessageException {
        for (Pair<Integer, Integer> contestant : contestants) {
            if (contestant.getKey() < 0 || contestant.getKey() >= SimulPar.NC)
                throw new MessageException("Invalid contestant number: " + contestant.getKey(), this);
        }
        this.contestants = contestants;
        return this;
    }

    /**
     * Roster getter
     *
     * @return Roster of the team
     */
    public int[] getRoster() {
        return roster;
    }

    /**
     * Roster setter
     *
     * @param roster New roster of the team
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setRoster(int[] roster) throws MessageException {
        for (int contestant : roster) {
            if (contestant < 0 || contestant >= SimulPar.NC)
                throw new MessageException("Invalid contestant: " + contestant, this);
        }
        this.roster = roster;
        return this;
    }

    /**
     * ContestantStrength getter
     *
     * @return Strength of all the contestants
     */
    public int[][] getContestantStrength() {
        return contestantStrength;
    }

    /**
     * ContestantStrength setter
     *
     * @param contestantStrength strength of all the contestants
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setContestantStrength(int[][] contestantStrength) throws MessageException {
        for (int[] contestant : contestantStrength) {
            for (int i = 0; i < SimulPar.NC; i++) {
                if (contestant[i] < SimulPar.MINS || contestant[i] > SimulPar.MAXS)
                    throw new MessageException("Invalid contestant strength: " + contestant[i], this);
            }
        }
        this.contestantStrength = contestantStrength;
        return this;
    }

    /**
     * LogFileName getter
     *
     * @return Name of the log file
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * LogFileName setter
     *
     * @param logFileName New name of the log file
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setLogFileName(String logFileName) throws MessageException {
        if (logFileName == null || logFileName.isEmpty())
            throw new MessageException("Invalid log file name: " + logFileName, this);
        this.logFileName = logFileName;
        return this;
    }

    /**
     * Position getter
     *
     * @return Position of the rope
     */
    public int getPosition() {
        return position;
    }

    /**
     * Position setter
     *
     * @param position New position of the rope
     * @return reference to this object
     */
    public Message setPosition(int position) {
        this.position = position;
        return this;
    }

    /**
     * Knockout getter
     *
     * @return Knockout flag
     */
    public boolean isKnockout() {
        return knockout;
    }

    /**
     * Knockout setter
     *
     * @param knockout New knockout flag
     * @return reference to this object
     */
    public Message setKnockout(boolean knockout) {
        this.knockout = knockout;
        return this;
    }

    /**
     * Score1 getter
     *
     * @return Score of team 1
     */
    public int getScore1() {
        return score1;
    }

    /**
     * Score1 setter
     *
     * @param score1 New score of team 1
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setScore1(int score1) throws MessageException {
        if (score1 < 0 || score1 > SimulPar.NG)
            throw new MessageException("Invalid score: " + score1, this);
        this.score1 = score1;
        return this;
    }

    /**
     * Score2 getter
     *
     * @return Score of team 2
     */
    public int getScore2() {
        return score2;
    }

    /**
     * Score2 setter
     *
     * @param score2 New score of team 2
     * @return reference to this object
     * @throws MessageException thrown if data is invalid
     */
    public Message setScore2(int score2) throws MessageException {
        if (score2 < 0 || score2 > SimulPar.NG)
            throw new MessageException("Invalid score: " + score2, this);
        this.score2 = score2;
        return this;
    }

}
