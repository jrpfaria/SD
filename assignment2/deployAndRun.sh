xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
xterm  -T "Referee Site" -hold -e "./RefereeSiteDeployAndRun.sh" &
xterm  -T "Playground" -hold -e "./PlaygroundDeployAndRun.sh" &
xterm  -T "Contestants Bench" -hold -e "./ContestantsBenchDeployAndRun.sh" &
sleep 1
xterm  -T "Referee" -hold -e "./RefereeDeployAndRun.sh" &
xterm  -T "Coaches" -hold -e "./CoachesDeployAndRun.sh" &
xterm  -T "Contestants" -hold -e "./ContestantsDeployAndRun.sh" &
