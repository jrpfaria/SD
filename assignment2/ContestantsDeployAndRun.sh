echo "Transfering data to the contestants node."
sshpass -f password ssh $USER@$CONTH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$CONTH 'rm -rf test/GameOfRope/dirContestants'
sshpass -f password scp dirContestants.zip $USER@$CONTH:test/GameOfRope
echo "Decompressing data sent to the contestants node."
sshpass -f password ssh $USER@$CONTH 'cd test/GameOfRope ; unzip -uq dirContestants.zip'
echo "Executing program at the contestants node."
sshpass -f password ssh $USER@$CONTH 'cd test/GameOfRope/dirContestants ; java clientSide.main.ClientGameOfRopeContestant '$GRH' '$GRP' '$PGH' '$PGP' '$CBH' '$CBP' stat'
