USER='sd102'
GRH='l040101-ws01.ua.pt'
GRP=22110
RSH='l040101-ws10.ua.pt'
RSP=22111
PGH='l040101-ws05.ua.pt'
PGP=22112
CBH='l040101-ws07.ua.pt'
CBP=22113
REFH='l040101-ws10.ua.pt'
COAH='l040101-ws08.ua.pt'
CONTH='l040101-ws09.ua.pt'

echo "Transfering data to the general repository node."
sshpass -f password ssh $USER@$GRH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$GRH 'rm -rf test/GameOfRope/dirGeneralRepos'
sshpass -f password scp dirGeneralRepos.zip $USER@$GRH:test/GameOfRope
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope/dirGeneralRepos ; java serverSide.main.ServerGameOfRopeGeneralRepos '$GRP''
echo "Server shutdown."
sshpass -f password ssh $USER@$GRH 'cd test/GameOfRope/dirGeneralRepos ; less stat'
