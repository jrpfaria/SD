export CLASSPATH=../lib/genclass.jar
echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."
echo "  RMI registry"
rm -rf dirRMIRegistry/*/
mkdir -p dirRMIRegistry dirRMIRegistry/interfaces
cp interfaces/*.class dirRMIRegistry/interfaces
echo "  Register Remote Objects"
rm -rf dirRegistry/*/
mkdir -p dirRegistry dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces
cp serverSide/main/ServerRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces
echo "  General Repository of Information"
rm -rf dirGeneralRepos/*/
mkdir -p dirGeneralRepos dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerGameOfRopeGeneralRepos.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRepos.class dirGeneralRepos/serverSide/objects
cp interfaces/Register.class interfaces/GeneralReposInterface.class dirGeneralRepos/interfaces
cp clientSide/entities/RefereeStates.class 'clientSide/entities/RefereeStates$1.class' clientSide/entities/CoachStates.class 'clientSide/entities/CoachStates$1.class' clientSide/entities/ContestantStates.class 'clientSide/entities/ContestantStates$1.class' dirGeneralRepos/clientSide/entities
cp commInfra/*.class dirGeneralRepos/commInfra
echo "  Referee Site"
rm -rf dirRefereeSite/*/
mkdir -p dirRefereeSite dirRefereeSite/serverSide dirRefereeSite/serverSide/main dirRefereeSite/serverSide/objects dirRefereeSite/interfaces \
         dirRefereeSite/clientSide dirRefereeSite/clientSide/entities dirRefereeSite/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerGameOfRopeRefereeSite.class dirRefereeSite/serverSide/main
cp serverSide/objects/RefereeSite.class dirRefereeSite/serverSide/objects
cp interfaces/*.class dirRefereeSite/interfaces
cp clientSide/entities/*.class dirRefereeSite/clientSide/entities
cp commInfra/*.class dirRefereeSite/commInfra
echo "  Playground"
rm -rf dirPlayground/*/
mkdir -p dirPlayground dirPlayground/serverSide dirPlayground/serverSide/main dirPlayground/serverSide/objects dirPlayground/interfaces \
         dirPlayground/clientSide dirPlayground/clientSide/entities dirPlayground/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerGameOfRopePlayground.class dirPlayground/serverSide/main
cp serverSide/objects/Playground.class dirPlayground/serverSide/objects
cp interfaces/*.class dirPlayground/interfaces
cp clientSide/entities/*.class dirPlayground/clientSide/entities
cp commInfra/*.class dirPlayground/commInfra
echo "  Contestants Bench"
rm -rf dirContestantsBench/*/
mkdir -p dirContestantsBench dirContestantsBench/serverSide dirContestantsBench/serverSide/main dirContestantsBench/serverSide/objects dirContestantsBench/interfaces \
         dirContestantsBench/clientSide dirContestantsBench/clientSide/entities dirContestantsBench/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerGameOfRopeContestantsBench.class dirContestantsBench/serverSide/main
cp serverSide/objects/ContestantsBench.class dirContestantsBench/serverSide/objects
cp interfaces/*.class dirContestantsBench/interfaces
cp clientSide/entities/*.class dirContestantsBench/clientSide/entities
cp commInfra/*.class dirContestantsBench/commInfra
echo "  Referee"
rm -rf dirReferee/*/
mkdir -p dirReferee dirReferee/serverSide dirReferee/serverSide/main dirReferee/clientSide dirReferee/clientSide/main dirReferee/clientSide/entities \
         dirReferee/interfaces dirReferee/commInfra
cp serverSide/main/SimulPar.class dirReferee/serverSide/main
cp clientSide/main/ClientGameOfRopeReferee.class dirReferee/clientSide/main
cp clientSide/entities/*.class dirReferee/clientSide/entities
cp interfaces/*.class dirReferee/interfaces
cp commInfra/*.class dirReferee/commInfra
echo "  Coaches"
rm -rf dirCoaches/*/
mkdir -p dirCoaches dirCoaches/serverSide dirCoaches/serverSide/main dirCoaches/clientSide dirCoaches/clientSide/main dirCoaches/clientSide/entities \
         dirCoaches/interfaces dirCoaches/commInfra
cp serverSide/main/SimulPar.class dirCoaches/serverSide/main
cp clientSide/main/ClientGameOfRopeReferee.class dirCoaches/clientSide/main
cp clientSide/entities/*.class dirCoaches/clientSide/entities
cp interfaces/*.class dirCoaches/interfaces
cp commInfra/*.class dirCoaches/commInfra
echo "  Contestants"
rm -rf dirContestants/*/
mkdir -p dirContestants dirContestants/serverSide dirContestants/serverSide/main dirContestants/clientSide dirContestants/clientSide/main dirContestants/clientSide/entities \
         dirContestants/interfaces dirContestants/commInfra
cp serverSide/main/SimulPar.class dirContestants/serverSide/main
cp clientSide/main/ClientGameOfRopeReferee.class dirContestants/clientSide/main
cp clientSide/entities/*.class dirContestants/clientSide/entities
cp interfaces/*.class dirContestants/interfaces
cp commInfra/*.class dirContestants/commInfra
echo "Compressing execution environments."
echo "  General Repository of Information"
echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry
echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry
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
