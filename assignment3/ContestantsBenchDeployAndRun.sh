echo "Transfering data to the contestants bench node."
sshpass -f password ssh $USER@$CBH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$CBH 'rm -rf test/GameOfRope/dirContestantsBench'
sshpass -f password scp dirContestantsBench.zip $USER@$CBH:test/GameOfRope
echo "Decompressing data sent to the contestants bench node."
sshpass -f password ssh $USER@$CBH 'cd test/GameOfRope ; unzip -uq dirContestantsBench.zip'
echo "Executing program at the contestants bench node."
sshpass -f password ssh $USER@$CBH 'cd test/GameOfRope/dirContestantsBench ; java serverSide.main.ServerGameOfRopeContestantsBench '$CBP' '$GRH' '$GRP''
