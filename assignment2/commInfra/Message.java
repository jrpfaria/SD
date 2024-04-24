package commInfra;

import clientSide.entities.*;
import java.io.*;

import genclass.GenericIO;

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

    public Message(MessageType msgType) {
        this.msgType = msgType;
    }

    public Message(MessageType msgType, int team) {
        this.msgType = msgType;
        this.team = team;
    }

    public Message(MessageType msgType, int team, int number) {
        this.msgType = msgType;
        this.team = team;
        this.number = number;
    }

    public Message(MessageType msgType, int team, int number, int strength) {
        this.msgType = msgType;
        this.team = team;
        this.number = number;
    }

    public MessageType getMsgType() {
        return this.msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public RefereeStates getRefereeState() {
        return refereeState;
    }

    public void setRefereeState(RefereeStates refereeState) {
        this.refereeState = refereeState;
    }

    public CoachStates getCoachState() {
        return coachState;
    }

    public void setCoachState(CoachStates coachState) {
        this.coachState = coachState;
    }

    public ContestantStates getContestantState() {
        return contestantState;
    }

    public void setContestantState(ContestantStates contestantState) {
        this.contestantState = contestantState;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
