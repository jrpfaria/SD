CODEBASE="file:///home/"$1"/test/GameOfRope/dirGeneralRepos/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopeGeneralRepos $GRP localhost $RMP
