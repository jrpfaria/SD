echo "Transfering data to the coaches node."
sshpass -f password ssh $USER@$COAH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$COAH 'rm -rf test/GameOfRope/dirCoaches'
sshpass -f password scp dirCoaches.zip $USER@$COAH:test/GameOfRope
echo "Decompressing data sent to the coaches node."
sshpass -f password ssh $USER@$COAH 'cd test/GameOfRope ; unzip -uq dirCoaches.zip'
echo "Executing program at the coaches node."
sshpass -f password scp config.sh $USER@$COAH:/home/$USER
sshpass -f password ssh $USER@$COAH 'source config.sh ; cd test/GameOfRope/dirCoaches ; ./coaches_com_d.sh'
