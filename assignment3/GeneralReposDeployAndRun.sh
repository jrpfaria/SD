echo "Transfering data to the general repository node."
sshpass -f password ssh $USER@$GRH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$GRH 'rm -rf test/GameOfRope/dirGeneralRepos'
sshpass -f password scp dirGeneralRepos.zip $USER@$GRH:test/GameOfRope
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope/dirGeneralRepos ; ./repos_com_d.sh '$USER''
echo "Server shutdown."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope/dirGeneralRepos ; less stat'
