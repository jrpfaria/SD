package clientSide.entities;

import interfaces.*;
import commInfra.Pair;
import genclass.GenericIO;
import serverSide.main.SimulPar;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;

/**
 * The Coach class represents a coach entity in the game simulation.
 * Coaches manage their teams, select players for each game, and participate in
 * various activities.
 */
public class Coach extends Thread {
    /**
     * Stores the index of the corresponding team
     */
    private final int team;
    /**
     * Stores the reference for the refereeSite shared area
     */
    private final RefereeSiteInterface refereeSiteStub;
    /**
     * Stores the reference for the playground shared area
     */
    private final PlaygroundInterface playgroundStub;
    /**
     * Stores the reference for the contestantsBench shared area
     */
    private final ContestantsBenchInterface contestantsBenchStub;
    /**
     * Stores the method which the coach will follow for selecting players
     */
    private final boolean method; // Randomly chosen: sweaty if true, gambler's dream if false
    /**
     * Stores the coach's state
     */
    private CoachStates state;
    /**
     * Auxiliary variable, added to keep track of the number of games played in the
     * gambler's dream method
     */
    private int gameCounter = 0;

    /**
     * Constructor for Coach class.
     * Initializes the coach with a specific team, referee site, playground, and
     * contestants bench.
     *
     * @param team                 The team index of the coach.
     * @param method               The coaching method: sweaty or gambler's dream
     * @param refereeSiteStub      The referee site stub.
     * @param playgroundStub       The playground stub.
     * @param contestantsBenchStub The contestants stub.
     */
    public Coach(int team, boolean method, RefereeSiteInterface refereeSiteStub, PlaygroundInterface playgroundStub,
            ContestantsBenchInterface contestantsBenchStub) {
        super(String.format("Coach-%d", team + 1));
        this.state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.method = method;
        this.refereeSiteStub = refereeSiteStub;
        this.playgroundStub = playgroundStub;
        this.contestantsBenchStub = contestantsBenchStub;
    }

    /**
     * Retrieves the team index of the coach.
     *
     * @return The team index of the coach.
     */
    public int getTeam() {
        return this.team;
    }

    public CoachStates getCoachState() {
        return this.state;
    }

    /**
     * Sets the state of the coach.
     *
     * @param state The state to set for the coach.
     */
    public void setCoachState(CoachStates state) {
        this.state = state;
    }

    /**
     * Selects players for the game based on a given list of contestants.
     *
     * @param contestants The list of contestants available for selection.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayers(Pair<Integer, Integer>[] contestants) {
        Pair<Integer, Integer>[] sorted = contestants.clone();
        Arrays.sort(sorted, Collections.reverseOrder()); // Sort contestants by strength (descending)
        if (method) // Sweaty method
            return selectPlayersSweaty(sorted);
        else // Gambler's Dream method
            return selectPlayersGamblersDream(sorted);
    }

    /**
     * Selects players using the Sweaty method, where the strongest players are
     * selected.
     *
     * @param sorted The sorted list of contestants.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayersSweaty(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP]; // Initialize roster array
        for (int i = 0; i < SimulPar.NP; i++)
            roster[i] = sorted[i].getKey(); // Select top players
        return roster;
    }

    /**
     * Selects players using the Gambler's Dream method, where the weakest players
     * are selected initially,
     * then the strongest players are selected in subsequent games after a certain
     * number of games.
     *
     * @param sorted The sorted list of contestants.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayersGamblersDream(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP]; // Initialize roster array
        if (gameCounter++ < SimulPar.NG * SimulPar.NT) { // Check if still within initial selection phase
            for (int i = 0; i < SimulPar.NP; i++)
                roster[i] = sorted[i].getKey(); // Select all players initially
        } else { // After initial phase, select only the weakest player
            for (int i = 0; i < SimulPar.NP; i++)
                roster[i] = sorted[SimulPar.NP - 1 - i].getKey();
        }
        return roster;
    }

    /**
     * Overrides the run method of Thread class.
     * This method represents the coach's behavior during the game.
     */
    @Override
    public void run() {
        int orders;
        Pair<Integer, Integer>[] contestants = reviewNotes(); // Review notes to help with assembling the team
        int[] roster = selectPlayers(contestants); // Select players for the game
        while (true) {
            orders = wait_for_referee_command(); // Wait for referee's command
            if (orders == 0)
                return; // Terminate if no further orders
            callContestants(roster); // Call selected contestants
            assemble_team(); // Assemble team on the playground
            informReferee(); // Inform referee that team is ready
            watch_trial(); // Watch the trial on the playground
            contestants = reviewNotes(); // Review notes for next game
            roster = selectPlayers(contestants); // Select players for next game
        }
    }

    private Pair<Integer, Integer>[] reviewNotes() {
        Pair<Integer, Integer>[] r = null;
        try {
            r = contestantsBenchStub.reviewNotes(team);
        } catch (RemoteException e) {
            GenericIO.writelnString("Coach " + (team + 1) + " remote exception on reviewNotes: " + e.getMessage());
            System.exit(1);
        }
        return r;
    }

    private int wait_for_referee_command() {
        int r = 0;
        try {
            contestantsBenchStub.wait_for_referee_command(team);
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Coach " + (team + 1) + " remote exception on wait_for_referee_command: " + e.getMessage());
            System.exit(1);
        }
        return r;
    }

    private void callContestants(int[] roster) {
        try {
            contestantsBenchStub.callContestants(team, roster);
        } catch (RemoteException e) {
            GenericIO.writelnString("Coach " + (team + 1) + " remote exception on callContestants: " + e.getMessage());
            System.exit(1);
        }
    }

    private void assemble_team() {
        try {
            playgroundStub.assemble_team(team);
        } catch (RemoteException e) {
            GenericIO.writelnString("Coach " + (team + 1) + " remote exception on assemble_team: " + e.getMessage());
            System.exit(1);
        }
    }

    private void informReferee() {
        try {
            refereeSiteStub.informReferee();
        } catch (RemoteException e) {
            GenericIO.writelnString("Coach " + (team + 1) + " remote exception on informReferee: " + e.getMessage());
            System.exit(1);
        }
    }

    private void watch_trial() {
        try {
            playgroundStub.watch_trial();
        } catch (RemoteException e) {
            GenericIO.writelnString("Coach " + (team + 1) + " remote exception on watch_trial: " + e.getMessage());
            System.exit(1);
        }
    }
}
