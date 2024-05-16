source config.sh

xterm  -T "RMI registry" -hold -e "./RMIRegistryDeployAndRun.sh" &
sleep 4
xterm  -T "Registry" -hold -e "./RegistryDeployAndRun.sh" &
sleep 4
xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
sleep 2
xterm  -T "Referee Site" -hold -e "./RefereeSiteDeployAndRun.sh" &
xterm  -T "Playground" -hold -e "./PlaygroundDeployAndRun.sh" &
xterm  -T "Contestants Bench" -hold -e "./ContestantsBenchDeployAndRun.sh" &
sleep 1
xterm  -T "Referee" -hold -e "./RefereeDeployAndRun.sh" &
xterm  -T "Coaches" -hold -e "./CoachesDeployAndRun.sh" &
xterm  -T "Contestants" -hold -e "./ContestantsDeployAndRun.sh" &
