echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."
echo "  General Repository of Information"
rm -rf dirGeneralRepos
mkdir -p dirGeneralRepos dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/entities dirGeneralRepos/serverSide/sharedRegions \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/*.class dirGeneralRepos/serverSide/main
cp serverSide/entities/*.class dirGeneralRepos/serverSide/entities
cp serverSide/sharedRegions/*.class dirGeneralRepos/serverSide/sharedRegions
cp clientSide/entities/*.class dirGeneralRepos/clientSide/entities
cp commInfra/*.class dirGeneralRepos/commInfra
echo "  Referee Site"
rm -rf dirRefereeSite
mkdir -p dirRefereeSite dirRefereeSite/serverSide dirRefereeSite/serverSide/main dirRefereeSite/serverSide/entities dirRefereeSite/serverSide/sharedRegions \
         dirRefereeSite/clientSide dirRefereeSite/clientSide/entities dirRefereeSite/clientSide/stubs dirRefereeSite/commInfra
cp serverSide/main/*.class dirRefereeSite/serverSide/main
cp serverSide/entities/*.class dirRefereeSite/serverSide/entities
cp serverSide/sharedRegions/*.class dirRefereeSite/serverSide/sharedRegions
cp clientSide/entities/*.class dirRefereeSite/clientSide/entities
cp clientSide/stubs/*.class dirRefereeSite/clientSide/stubs
cp commInfra/*.class dirRefereeSite/commInfra
echo "  Playground"
rm -rf dirPlayground
mkdir -p dirPlayground dirPlayground/serverSide dirPlayground/serverSide/main dirPlayground/serverSide/entities dirPlayground/serverSide/sharedRegions \
         dirPlayground/clientSide dirPlayground/clientSide/entities dirPlayground/clientSide/stubs dirPlayground/commInfra
cp serverSide/main/*.class dirPlayground/serverSide/main
cp serverSide/entities/*.class dirPlayground/serverSide/entities
cp serverSide/sharedRegions/*.class dirPlayground/serverSide/sharedRegions
cp clientSide/entities/*.class dirPlayground/clientSide/entities
cp clientSide/stubs/*.class dirPlayground/clientSide/stubs
cp commInfra/*.class dirPlayground/commInfra
echo "  Contestants Bench"
rm -rf dirContestantsBench
mkdir -p dirContestantsBench dirContestantsBench/serverSide dirContestantsBench/serverSide/main dirContestantsBench/serverSide/entities dirContestantsBench/serverSide/sharedRegions \
         dirContestantsBench/clientSide dirContestantsBench/clientSide/entities dirContestantsBench/clientSide/stubs dirContestantsBench/commInfra
cp serverSide/main/*.class dirContestantsBench/serverSide/main
cp serverSide/entities/*.class dirContestantsBench/serverSide/entities
cp serverSide/sharedRegions/*.class dirContestantsBench/serverSide/sharedRegions
cp clientSide/entities/*.class dirContestantsBench/clientSide/entities
cp clientSide/stubs/*.class dirContestantsBench/clientSide/stubs
cp commInfra/*.class dirContestantsBench/commInfra
echo "  Referee"
rm -rf dirReferee
mkdir -p dirReferee dirReferee/serverSide dirReferee/serverSide/main dirReferee/clientSide dirReferee/clientSide/main dirReferee/clientSide/entities \
         dirReferee/clientSide/stubs dirReferee/commInfra
cp serverSide/main/*.class dirReferee/serverSide/main
cp clientSide/main/*.class dirReferee/clientSide/main
cp clientSide/entities/*.class dirReferee/clientSide/entities
cp clientSide/stubs/*.class dirReferee/clientSide/stubs
cp commInfra/*.class dirReferee/commInfra
echo "  Coaches"
rm -rf dirCoaches
mkdir -p dirCoaches dirCoaches/serverSide dirCoaches/serverSide/main dirCoaches/clientSide dirCoaches/clientSide/main dirCoaches/clientSide/entities \
         dirCoaches/clientSide/stubs dirCoaches/commInfra
cp serverSide/main/*.class dirCoaches/serverSide/main
cp clientSide/main/*.class dirCoaches/clientSide/main
cp clientSide/entities/*.class dirCoaches/clientSide/entities
cp clientSide/stubs/*.class dirCoaches/clientSide/stubs
cp commInfra/*.class dirCoaches/commInfra
echo "  Contestants"
rm -rf dirContestants
mkdir -p dirContestants dirContestants/serverSide dirContestants/serverSide/main dirContestants/clientSide dirContestants/clientSide/main dirContestants/clientSide/entities \
         dirContestants/clientSide/stubs dirContestants/commInfra
cp serverSide/main/*.class dirContestants/serverSide/main
cp clientSide/main/*.class dirContestants/clientSide/main
cp clientSide/entities/*.class dirContestants/clientSide/entities
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
