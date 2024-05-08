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

echo "Transfering data to the playground node."
sshpass -f password ssh $USER@$PGH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$PGH 'rm -rf test/GameOfRope/dirPlayground'
sshpass -f password scp dirPlayground.zip $USER@$PGH:test/GameOfRope
echo "Decompressing data sent to the playground node."
sshpass -f password ssh $USER@$PGH 'cd test/GameOfRope ; unzip -uq dirPlayground.zip'
echo "Executing program at the playground node."
sshpass -f password ssh $USER@$PGH 'cd test/GameOfRope/dirPlayground ; java serverSide.main.ServerGameOfRopePlayground '$PGP' '$GRH' '$GRP''
