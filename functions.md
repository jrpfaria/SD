### Contestants Bench
```java

void callContestants(short team, short[] contestants)
//Coach
//change variables "called" depending on the team
void synchronized seatDown(short team, short contestant, short strength)
//Contestant
//removes strength from "strength" variable, moves to bench
void synchronized declareMatchWinner(short team)
//Referee
//inform entities that the match is over
void synchronized callTrial()
//Referee
//inform coaches to assemble teams and get ready
```

### Referee Site
```java

void synchronized announceNewGame()
//Referee
//inform coaches of new game
void synchronized informReferee()
//Coach
//informs referee that teams are ready
```

### Playground
```java

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
void synchronized declareGameWinner(short team)
//Referee
//inform coaches of who the winner of the game was
void followCoachAdvice()
//Contestant
//checks if own nubmer is in "called" variable. if so, contestant was called
void pullTheRope()
//Constestant
//pull the rope
```
