CODEBASE="file:///home/"$1"/test/GameOfRope/dirReferee/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeReferee localhost $RMP
