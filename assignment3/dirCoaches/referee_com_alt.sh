CODEBASE="file:///home/"$1"/test/GameOfRope/dirCoaches/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeCoach localhost $RMP
