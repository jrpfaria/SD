CODEBASE="file:///home/"$1"/test/GameOfRope/dirContestants/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeContestant localhost $RMP stat
