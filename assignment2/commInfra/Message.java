package commInfra;

import clientSide.entities.*;

import java.io.Serializable;

public class Message implements Serializable { // TODO
    private static final long serialVersionUID = 2021L;

    private MessageType msgType;
    private RefereeStates refereeState = null;
    private CoachStates coachState = null;
    private ContestantStates contestantState = null;
    private int team = -1;
    private int number = -1;
    private int strength = -1;
    private int value = -1;
    private Pair<Integer, Integer>[] contestants;
    private int[] roster;
    private int[][] contestantStrength;
    private String logFileName = null;
    private int position = 0;
    private boolean knockout = false;
    private int score1 = -1, score2 = -1;

    public Message(MessageType msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return this.msgType.toString() + " team:" + team + " number:" + number;
    }

    public MessageType getMsgType() {
        return this.msgType;
    }

    public Message setMsgType(MessageType msgType) {
        this.msgType = msgType;
        return this;
    }

    public RefereeStates getRefereeState() {
        return refereeState;
    }

    public Message setRefereeState(RefereeStates refereeState) {
        this.refereeState = refereeState;
        return this;
    }

    public CoachStates getCoachState() {
        return coachState;
    }

    public Message setCoachState(CoachStates coachState) {
        this.coachState = coachState;
        return this;
    }

    public ContestantStates getContestantState() {
        return contestantState;
    }

    public Message setContestantState(ContestantStates contestantState) {
        this.contestantState = contestantState;
        return this;
    }

    public int getTeam() {
        return team;
    }

    public Message setTeam(int team) {
        this.team = team;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Message setNumber(int number) {
        this.number = number;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public Message setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Message setValue(int value) {
        this.value = value;
        return this;
    }

    public Pair<Integer, Integer>[] getContestants() {
        return contestants;
    }

    public Message setContestants(Pair<Integer, Integer>[] contestants) {
        this.contestants = contestants;
        return this;
    }

    public int[] getRoster() {
        return roster;
    }

    public Message setRoster(int[] roster) {
        this.roster = roster;
        return this;
    }

    public int[][] getContestantStrength() {
        return contestantStrength;
    }

    public Message setContestantStrength(int[][] contestantStrength) {
        this.contestantStrength = contestantStrength;
        return this;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public Message setLogFileName(String logFileName) {
        this.logFileName = logFileName;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public Message setPosition(int position) {
        this.position = position;
        return this;
    }

    public boolean isKnockout() {
        return knockout;
    }

    public Message setKnockout(boolean knockout) {
        this.knockout = knockout;
        return this;
    }

    public int getScore1() {
        return score1;
    }

    public Message setScore1(int score1) {
        this.score1 = score1;
        return this;
    }

    public int getScore2() {
        return score2;
    }

    public Message setScore2(int score2) {
        this.score2 = score2;
        return this;
    }

}
