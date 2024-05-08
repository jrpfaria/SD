echo "Transfering data to the playground node."
sshpass -f password ssh $USER@$PGH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$PGH 'rm -rf test/GameOfRope/dirPlayground'
sshpass -f password scp dirPlayground.zip $USER@$PGH:test/GameOfRope
echo "Decompressing data sent to the playground node."
sshpass -f password ssh $USER@$PGH 'cd test/GameOfRope ; unzip -uq dirPlayground.zip'
echo "Executing program at the playground node."
sshpass -f password ssh $USER@$PGH 'cd test/GameOfRope/dirPlayground ; java serverSide.main.ServerGameOfRopePlayground '$PGP' '$GRH' '$GRP''
