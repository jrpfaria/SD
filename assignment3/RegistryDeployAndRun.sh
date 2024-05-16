echo "Transfering data to the registry node."
sshpass -f password ssh $USER@$RRH 'mkdir -p test/GameOfRope'
sshpass -f password scp dirRegistry.zip $USER@$RRH:test/GameOfRope
echo "Decompressing data sent to the registry node."
sshpass -f password ssh $USER@$RRH 'cd test/GameOfRope ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password scp config.sh $USER@$RRH:/home/$USER
sshpass -f password ssh $USER@$RRH 'source config.sh ; cd test/GameOfRope/dirRegistry ; ./registry_com_d.sh '$USER''
