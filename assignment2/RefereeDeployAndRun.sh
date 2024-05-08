USER='sd102'
GRH='l040101-ws01.ua.pt'
GRP=22110
RSH='l040101-ws05.ua.pt'
RSP=22111
PGH='l040101-ws06.ua.pt'
PGP=22112
CBH='l040101-ws07.ua.pt'
CBP=22113
REFH='l040101-ws08.ua.pt'
COAH='l040101-ws09.ua.pt'
CONTH='l040101-ws10.ua.pt'

echo "Transfering data to the referee node."
sshpass -f password ssh $USER@$REFH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$REFH 'rm -rf test/GameOfRope/dirReferee'
sshpass -f password scp dirReferee.zip $USER@$REFH:test/GameOfRope
echo "Decompressing data sent to the referee node."
sshpass -f password ssh $USER@$REFH 'cd test/GameOfRope ; unzip -uq dirReferee.zip'
echo "Executing program at the referee node."
sshpass -f password ssh $USER@$REFH 'cd test/GameOfRope/dirReferee ; java clientSide.main.ClientGameOfRopeReferee '$GRH' '$GRP' '$RSH' '$RSP' '$PGH' '$PGP' '$CBH' '$CBP''
