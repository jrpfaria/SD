echo "Transfering data to the contestants node."
sshpass -f password ssh $USER@$CONTH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$CONTH 'rm -rf test/GameOfRope/dirContestants'
sshpass -f password scp dirContestants.zip $USER@$CONTH:test/GameOfRope
echo "Decompressing data sent to the contestants node."
sshpass -f password ssh $USER@$CONTH 'cd test/GameOfRope ; unzip -uq dirContestants.zip'
echo "Executing program at the contestants node."
sshpass -f password scp config.sh $USER@$CONTH:/home/$USER
sshpass -f password ssh $USER@$CONTH 'source config.sh ; cd test/GameOfRope/dirContestants ; ./contestants_com_d.sh'
