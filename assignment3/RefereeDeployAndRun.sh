echo "Transfering data to the referee node."
sshpass -f password ssh $USER@$REFH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$REFH 'rm -rf test/GameOfRope/dirReferee'
sshpass -f password scp dirReferee.zip $USER@$REFH:test/GameOfRope
echo "Decompressing data sent to the referee node."
sshpass -f password ssh $USER@$REFH 'cd test/GameOfRope ; unzip -uq dirReferee.zip'
echo "Executing program at the referee node."
sshpass -f password ssh $USER@$REFH 'cd test/GameOfRope/dirReferee ; java clientSide.main.ClientGameOfRopeReferee '$GRH' '$GRP' '$RSH' '$RSP' '$PGH' '$PGP' '$CBH' '$CBP''
