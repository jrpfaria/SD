echo "Transfering data to the RMIregistry node."
sshpass -f password ssh $USER@$RMH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$RMH 'rm -rf test/GameOfRope/*'
sshpass -f password ssh $USER@$RMH 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh $USER@$RMH 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip $USER@$RMH:test/GameOfRope
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh $USER@$RMH 'cd test/GameOfRope ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh $USER@$RMH 'cd test/GameOfRope/dirRMIRegistry ; cp interfaces/*.class /home/'$USER'/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/'$USER''
echo "Executing program at the RMIregistry node."
sshpass -f password ssh $USER@$RMH './set_rmiregistry_d.sh '$USER' '$RMP''
