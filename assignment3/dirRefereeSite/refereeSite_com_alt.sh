CODEBASE="file:///home/"$1"/test/GameOfRope/dirRefereeSite/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopeRefereeSite $RSP localhost $RMP
