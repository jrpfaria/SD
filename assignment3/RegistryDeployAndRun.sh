echo "Transfering data to the registry node."
sshpass -f password ssh $USER@$RMH 'mkdir -p test/GameOfRope'
sshpass -f password scp dirRegistry.zip $USER@$RMH:test/GameOfRope
echo "Decompressing data sent to the registry node."
sshpass -f password ssh $USER@$RMH 'cd test/GameOfRope ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password ssh $USER@$RMH 'cd test/GameOfRope/dirRegistry ; ./registry_com_d.sh '$USER''
