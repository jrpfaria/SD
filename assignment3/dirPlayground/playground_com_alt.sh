CODEBASE="file:///home/"$1"/test/GameOfRope/dirPlayground/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopePlayground $PGP localhost $RMP