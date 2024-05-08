export CLASSPATH=../lib/genclass.jar
echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."
echo "  General Repository of Information"
rm -rf dirGeneralRepos
mkdir -p dirGeneralRepos dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/entities dirGeneralRepos/serverSide/sharedRegions \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/ServerGameOfRopeGeneralRepos.class serverSide/main/SimulPar.class dirGeneralRepos/serverSide/main
cp serverSide/entities/GeneralReposClientProxy.class dirGeneralRepos/serverSide/entities
cp serverSide/sharedRegions/GeneralRepos.class serverSide/sharedRegions/GeneralReposInterface.class 'serverSide/sharedRegions/GeneralReposInterface$1.class' dirGeneralRepos/serverSide/sharedRegions
cp clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirGeneralRepos/clientSide/entities
cp commInfra/*.class dirGeneralRepos/commInfra
echo "  Referee Site"
rm -rf dirRefereeSite
mkdir -p dirRefereeSite dirRefereeSite/serverSide dirRefereeSite/serverSide/main dirRefereeSite/serverSide/entities dirRefereeSite/serverSide/sharedRegions \
         dirRefereeSite/clientSide dirRefereeSite/clientSide/entities dirRefereeSite/clientSide/stubs dirRefereeSite/commInfra
cp serverSide/main/ServerGameOfRopeRefereeSite.class serverSide/main/SimulPar.class dirRefereeSite/serverSide/main
cp serverSide/entities/RefereeSiteClientProxy.class dirRefereeSite/serverSide/entities
cp serverSide/sharedRegions/RefereeSite.class serverSide/sharedRegions/RefereeSiteInterface.class 'serverSide/sharedRegions/RefereeSiteInterface$1.class' dirRefereeSite/serverSide/sharedRegions
cp clientSide/entities/RefereeCloning.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/CoachCloning.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/ContestantCloning.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirRefereeSite/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirRefereeSite/clientSide/stubs
cp commInfra/*.class dirRefereeSite/commInfra
echo "  Playground"
rm -rf dirPlayground
mkdir -p dirPlayground dirPlayground/serverSide dirPlayground/serverSide/main dirPlayground/serverSide/entities dirPlayground/serverSide/sharedRegions \
         dirPlayground/clientSide dirPlayground/clientSide/entities dirPlayground/clientSide/stubs dirPlayground/commInfra
cp serverSide/main/ServerGameOfRopePlayground.class serverSide/main/SimulPar.class dirPlayground/serverSide/main
cp serverSide/entities/PlaygroundClientProxy.class dirPlayground/serverSide/entities
cp serverSide/sharedRegions/Playground.class serverSide/sharedRegions/PlaygroundInterface.class 'serverSide/sharedRegions/PlaygroundInterface$1.class' dirPlayground/serverSide/sharedRegions
cp clientSide/entities/RefereeCloning.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/CoachCloning.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/ContestantCloning.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirPlayground/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirPlayground/clientSide/stubs
cp commInfra/*.class dirPlayground/commInfra
echo "  Contestants Bench"
rm -rf dirContestantsBench
mkdir -p dirContestantsBench dirContestantsBench/serverSide dirContestantsBench/serverSide/main dirContestantsBench/serverSide/entities dirContestantsBench/serverSide/sharedRegions \
         dirContestantsBench/clientSide dirContestantsBench/clientSide/entities dirContestantsBench/clientSide/stubs dirContestantsBench/commInfra
cp serverSide/main/ServerGameOfRopeContestantsBench.class serverSide/main/SimulPar.class dirContestantsBench/serverSide/main
cp serverSide/entities/ContestantsBenchClientProxy.class dirContestantsBench/serverSide/entities
cp serverSide/sharedRegions/ContestantsBench.class serverSide/sharedRegions/ContestantsBenchInterface.class 'serverSide/sharedRegions/ContestantsBenchInterface$1.class' dirContestantsBench/serverSide/sharedRegions
cp clientSide/entities/RefereeCloning.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/CoachCloning.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/ContestantCloning.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirContestantsBench/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirContestantsBench/clientSide/stubs
cp commInfra/*.class dirContestantsBench/commInfra
echo "  Referee"
rm -rf dirReferee
mkdir -p dirReferee dirReferee/serverSide dirReferee/serverSide/main dirReferee/clientSide dirReferee/clientSide/main dirReferee/clientSide/entities \
         dirReferee/clientSide/stubs dirReferee/commInfra
cp serverSide/main/SimulPar.class dirReferee/serverSide/main
cp clientSide/main/ClientGameOfRopeReferee.class dirReferee/clientSide/main
cp clientSide/entities/Referee.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/Coach.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/Contestant.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirReferee/clientSide/entities
cp clientSide/stubs/*.class dirReferee/clientSide/stubs
cp commInfra/*.class dirReferee/commInfra
echo "  Coaches"
rm -rf dirCoaches
mkdir -p dirCoaches dirCoaches/serverSide dirCoaches/serverSide/main dirCoaches/clientSide dirCoaches/clientSide/main dirCoaches/clientSide/entities \
         dirCoaches/clientSide/stubs dirCoaches/commInfra
cp serverSide/main/SimulPar.class dirCoaches/serverSide/main
cp clientSide/main/ClientGameOfRopeCoach.class dirCoaches/clientSide/main
cp clientSide/entities/Referee.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/Coach.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/Contestant.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirCoaches/clientSide/entities
cp clientSide/stubs/*.class dirCoaches/clientSide/stubs
cp commInfra/*.class dirCoaches/commInfra
echo "  Contestants"
rm -rf dirContestants
mkdir -p dirContestants dirContestants/serverSide dirContestants/serverSide/main dirContestants/clientSide dirContestants/clientSide/main dirContestants/clientSide/entities \
         dirContestants/clientSide/stubs dirContestants/commInfra
cp serverSide/main/SimulPar.class dirContestants/serverSide/main
cp clientSide/main/ClientGameOfRopeContestant.class dirContestants/clientSide/main
cp clientSide/entities/Referee.class clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/Coach.class clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/Contestant.class clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirContestants/clientSide/entities
cp clientSide/stubs/*.class dirContestants/clientSide/stubs
cp commInfra/*.class dirContestants/commInfra
echo "Compressing execution environments."
echo "  General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
echo "  Referee Site"
rm -f  dirRefereeSite.zip
zip -rq dirRefereeSite.zip dirRefereeSite
echo "  Playground"
rm -f  dirPlayground.zip
zip -rq dirPlayground.zip dirPlayground
echo "  Contestants Bench"
rm -f  dirContestantsBench.zip
zip -rq dirContestantsBench.zip dirContestantsBench
echo "  Referee"
rm -f  dirReferee.zip
zip -rq dirReferee.zip dirReferee
echo "  Coaches"
rm -f  dirCoaches.zip
zip -rq dirCoaches.zip dirCoaches
echo "  Contestants"
rm -f  dirContestants.zip
zip -rq dirContestants.zip dirContestants
