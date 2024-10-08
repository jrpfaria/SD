echo "Transfering data to the referee site node."
sshpass -f password ssh $USER@$RSH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$RSH 'rm -rf test/GameOfRope/dirRefereeSite'
sshpass -f password scp dirRefereeSite.zip $USER@$RSH:test/GameOfRope
echo "Decompressing data sent to the referee site node."
sshpass -f password ssh $USER@$RSH 'cd test/GameOfRope ; unzip -uq dirRefereeSite.zip'
echo "Executing program at the referee site node."
sshpass -f password ssh $USER@$RSH 'cd test/GameOfRope/dirRefereeSite ; java serverSide.main.ServerGameOfRopeRefereeSite '$RSP' '$GRH' '$GRP''
