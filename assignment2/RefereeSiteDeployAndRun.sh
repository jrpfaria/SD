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

echo "Transfering data to the referee site node."
sshpass -f password ssh $USER@$RSH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$RSH 'rm -rf test/GameOfRope/dirRefereeSite'
sshpass -f password scp dirRefereeSite.zip $USER@$RSH:test/GameOfRope
echo "Decompressing data sent to the referee site node."
sshpass -f password ssh $USER@$RSH 'cd test/GameOfRope ; unzip -uq dirRefereeSite.zip'
echo "Executing program at the referee site node."
sshpass -f password ssh $USER@$RSH 'cd test/GameOfRope/dirRefereeSite ; java serverSide.main.ServerGameOfRopeRefereeSite '$RSP' '$GRH' '$GRP''
