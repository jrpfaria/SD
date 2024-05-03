package commInfra;

import clientSide.entities.*;
import java.io.*;

public class Message implements Serializable { // TODO
    private static final long serialVersionUID = 2021L;
    
    private MessageType msgType;
    private RefereeStates refereeState;
    private CoachStates coachState;
    private ContestantStates contestantState;
    private int team;
    private int number;
    private int strength;
    private String fileName;
    private int value;
    private Pair<Integer, Integer>[] contestants;
    private int[] roster;
    private int[][] contestantStrength;
    private String logFileName;
    private int position;
    private boolean knockout;
    private int score1, score2;

    public Message(MessageType msgType) {
        this.msgType = msgType;
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

    public String getFileName() {
        return fileName;
    }

    public Message setFileName(String fileName) {
        this.fileName = fileName;
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

    // public Message(MessageType msgType, RefereeStates state) {
    //     this.msgType = msgType;
    //     this.refereeState = state;
    // }

    // public Message(MessageType msgType, CoachStates state) {
    //     this.msgType = msgType;
    //     this.coachState = state;
    // }

    // public Message(MessageType msgType, ContestantStates state) {
    //     this.msgType = msgType;
    //     this.contestantState = state;
    // }

    // public Message(MessageType msgType, int team, CoachStates state) {
    //     this.msgType = msgType;
    //     this.team = team;
    //     this.coachState = state;
    // }

    // public Message(MessageType msgType, int team, int number, ContestantStates state) {
    //     this.msgType = msgType;
    //     this.team = team;
    //     this.number = number;
    //     this.contestantState = state;
    // }

    // public Message(MessageType msgType, int team) {
    //     this.msgType = msgType;
    //     this.team = team;
    // }

    // public Message(MessageType msgType, int a, int b) {
    //     this.msgType = msgType;
    //     switch (msgType) {
    //         case DMW:
    //             this.score1 = a;
    //             this.score2 = b;
    //             break;
    //         default: break;
    //     }
    // }

    // public Message(MessageType msgType, int team, int number, int strength) {
    //     this.msgType = msgType;
    //     this.team = team;
    //     this.number = number;
    // }

    // public Message(MessageType msgType, int team, int[] roster) {
    //     this.msgType = msgType;
    //     this.team = team;
    //     this.roster = roster;
    // }

    // public Message(MessageType msgType, String logFileName, int[][] contestantStrength) {
    //     this.msgType = msgType;
    //     this.logFileName = logFileName;
    //     this.contestantStrength = contestantStrength;
    // }

    // public Message(MessageType msgType, int position, boolean knockout) {
    //     this.msgType = msgType;
    //     this.position = position;
    //     this.knockout = knockout;
    // }

}
