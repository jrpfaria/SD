### Contestants Bench
```java
short[] calledA;
short[] calledB;

Contestant[] teamA = new Contestant[5];
Contestant[] teamB = new Contestant[5];

void callContestants(short team, short[] contestants)
//Coach
//change variables "called" depending on the team
void followCoachAdvice()
//Contestant
//checks if own nubmer is in "called" variable. if so, contestant was called
void synchronized seatDown(short team, short contestant, short strength)
//Contestant
//removes strength from "strength" variable, moves to bench
```

### Referee Site
```java
short gamesPlayed;
short trialsPlayed;
short ropeDifference;
short scoreDifference;

void synchronized announceNewGame()
//Referee
//inform coaches of new game
void synchronized callTrial()
//Referee
//inform coaches to assemble teams and get ready
void synchronized declareGameWinner(short team)
//Referee
//inform coaches of who the winner of the game was
void synchronized declareMatchWinner(short team)
//inform coaches of who the winner of the match was
void synchronized informReferee()
//Coach
//informs referee that teams are ready
```

### Playground
```java
int strengthA = 0;
int strengthB = 0;

Contestant[] teamA = new Contestant[3];
Contestant[] teamB = new Contestant[3];

void synchronized startTrial()
//Referee
//inform coaches and contestants that trial is starting
void synchronized pullTheRope(short team, short strength)
//Contestant
//add strength to "strength" variable (depending on team)
short synchronized assertTrialDecision()
//Referee
//check rope
void synchronized amDone()
//Contestant
//informs referee that effort is complete
```
BLOCKING_STATE = function_that_led_to_state -> function_that_unblocks_thread

Ref.TEAMS_READY = callTrial -> informReferee
Ref.WAIT_FOR_TRIAL_CONCLUSION = startTrial -> amDone
Coach.WAIT_FOR_REFEREE_COMMAND = review_notes -> callTrial
Coach.ASSEMBLE_TEAM = callContestants -> followCoachAdvice
Coach.WATCH_TRIAL = informReferee -> assertTrialDecision
Con.SEAT_AT_THE_BENCH = seatDown -> callContestants
Con.STAND_IN_POSISION = followCoachAdvice -> startTrial
Con.DO_YOUR_BEST = amDone -> assertTrialDecision

amDone + assertTrialDecicion + informReferee + callTrial + startTrial + followCoachAdvice + callContestants + seatDown + review_notes
