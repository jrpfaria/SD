package commInfra;

import clientSide.entities.*;

import java.io.Serializable;

/**
 * The Message class is used to represent a message that can be sent between entities.
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
    private int[][] con
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
     */
    public Message(MessageType msgType) {
        this.msgType = msgType;
    }

    /**
     * Message toString.
     * @return String representation of the message
     */
    @Override
    public String toString() {
        return this.msgType.toString() + " team:" + team + " number:" + number;
    }

    /**
     * MsgType getter
     * @return Type of the message
     */
    public MessageType getMsgType() {
        return this.msgType;
    }

    /**
     * MsgType setter
     * @return reference to this object
     */
    public Message setMsgType(MessageType msgType) {
        if (Enum.isDefined(MessageType.class, msgType) == false)
            throw new MessageException("Invalid message type: " + msgType);
        this.msgType = msgType;
        return this;
    }

    /**
     * RefereeState getter
     * @return State of the referee
     */
    public RefereeStates getRefereeState() {
        return refereeState;
    }

    /**
     * RefereeState setter
     * @return reference to this object
     */
    public Message setRefereeState(RefereeStates refereeState) {
        if (Enum.isDefined(RefereeStates.class, refereeState) == false)
            throw new MessageException("Invalid referee state: " + refereeState);
        this.refereeState = refereeState;
        return this;
    }

    /**
     * CoachState getter
     * @return State of the coach
     */
    public CoachStates getCoachState() {
        return coachState;
    }

    /**
     * CoachState setter
     * @return reference to this object
     */
    public Message setCoachState(CoachStates coachState) {
        if (Enum.isDefined(CoachStates.class, coachState) == false)
            throw new MessageException("Invalid coach state: " + coachState);
        this.coachState = coachState;
        return this;
    }

    /**
     * ContestantState getter
     * @return State of the contestant
     */
    public ContestantStates getContestantState() {
        return contestantState;
    }

    /**
     * ContestantState setter
     * @return reference to this object
     */
    public Message setContestantState(ContestantStates contestantState) {
        if (Enum.isDefined(ContestantStates.class, contestant) == false)
            throw new MessageException("Invalid contestant state: " + contestantState);
        this.contestantState = contestantState;
        return this;
    }

    /**
     * Team getter
     * @return Team number
     */
    public int getTeam() {
        return team;
    }

    /**
     * Team setter
     * @return reference to this object
     */
    public Message setTeam(int team) {
        if (team != 0 && team != 1)
            throw new MessageException("Invalid team number: " + team);
        this.team = team;
        return this;
    }

    /**
     * Number getter
     * @return Contestant number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Number setter
     * @return reference to this object
     */
    public Message setNumber(int number) {
        if (number < 0 || number >= SimulPar.NC)
            throw new MessageException("Invalid number: " + number);
        this.number = number;
        return this;
    }

    /**
     * Strength getter
     * @return Strength of the contestant
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Strength setter
     * @return reference to this object
     */
    public Message setStrength(int strength) {
        if (strength == null)
            throw new MessageException("Invalid strength: " + strength);
        this.strength = strength;
        return this;
    }

    /**
     * Value getter
     * @return Value of the message
     */
    public int getValue() {
        return value;
    }

    /**
     * Value setter
     * @return reference to this object
     */
    public Message setValue(int value) {
        if (value == null)
            throw new MessageException("Invalid value: " + value);
        this.value = value;
        return this;
    }

    /**
     * Contestants getter
     * @return Contestants in the game
     */
    public Pair<Integer, Integer>[] getContestants() {
        return contestants;
    }

    /**
     * Contestants setter
     * @return reference to this object
     */
    public Message setContestants(Pair<Integer, Integer>[] contestants) {
        for (Pair<Integer, Integer> contestant : contestants) {
            if (contestant.getKey() < 0 || contestant.getKey() >= SimulPar.NC)
                throw new MessageException("Invalid contestant: " + contestant);
        }
        this.contestants = contestants;
        return this;
    }

    /**
     * Roster getter
     * @return Roster of the team
     */
    public int[] getRoster() {
        return roster;
    }

    /**
     * Roster setter
     * @return reference to this object
     */
    public Message setRoster(int[] roster) {
        for (int contestant : roster) {
            if (contestant < 0 || contestant >= SimulPar.NC)
                throw new MessageException("Invalid contestant: " + contestant);
        }
        this.roster = roster;
        return this;
    }

    /**
     * ContestantStrength getter
     * @return Strength of all the contestants
     */
    public int[][] getContestantStrength() {
        return contestantStrength;
    }

    /**
     * ContestantStrength setter
     * @return reference to this object
     */
    public Message setContestantStrength(int[][] contestantStrength) {
        for (int[] contestant : contestantStrength) {
            if (contestant[0] < 0 || contestant[0] >= SimulPar.NC)
                throw new MessageException("Invalid contestant: " + contestant);
            if (contestant[1] < SimulPar.MINS || contestant[1] >= SimulPar.MAXS)
                throw new MessageException("Invalid contestant: " + contestant
        }
        this.contestantStrength = contestantStrength;
        return this;
    }

    /**
     * LogFileName getter
     * @return Name of the log file
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * LogFileName setter
     * @return reference to this object
     */
    public Message setLogFileName(String logFileName) {
        if (logFileName == null || logFileName.isEmpty())
            throw new MessageException("Invalid log file name: " + logFileName);
        this.logFileName = logFileName;
        return this;
    }

    /**
     * Position getter
     * @return Position of the rope
     */
    public int getPosition() {
        return position;
    }

    /**
     * Position setter
     * @return reference to this object
     */
    public Message setPosition(int position) {
        if (position < 0 || position >= SimulPar.NP)
            throw new MessageException("Invalid position: " + position);
        this.position = position;
        return this;
    }

    /**
     * Knockout getter
     * @return Knockout flag
     */
    public boolean isKnockout() {
        return knockout;
    }

    /**
     * Knockout setter
     * @return reference to this object
     */
    public Message setKnockout(boolean knockout) {
        if (knockout == null)
            throw new MessageException("Invalid knockout: " + knockout);
        this.knockout = knockout;
        return this;
    }

    /**
     * Score1 getter
     * @return Score of team 1
     */
    public int getScore1() {
        return score1;
    }

    /**
     * Score1 setter
     * @return reference to this object
     */
    public Message setScore1(int score1) {
        if (score1 < 0 || score1 > SimulPar.NG)
            throw new MessageException("Invalid score: " + score1);
        this.score1 = score1;
        return this;
    }

    /**
     * Score2 getter
     * @return Score of team 2
     */
    public int getScore2() {
        return score2;
    }

    /**
     * Score2 setter
     * @return reference to this object
     */
    public Message setScore2(int score2) {
        if (score2 < 0 || score2 > SimulPar.NG)
            throw new MessageException("Invalid score: " + score2);
        this.score2 = score2;
        return this;
    }

}
