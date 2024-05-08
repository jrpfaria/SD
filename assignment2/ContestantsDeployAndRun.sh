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

echo "Transfering data to the contestants node."
sshpass -f password ssh $USER@$CONTH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$CONTH 'rm -rf test/GameOfRope/dirContestants'
sshpass -f password scp dirContestants.zip $USER@$CONTH:test/GameOfRope
echo "Decompressing data sent to the contestants node."
sshpass -f password ssh $USER@$CONTH 'cd test/GameOfRope ; unzip -uq dirContestants.zip'
echo "Executing program at the contestants node."
sshpass -f password ssh $USER@$CONTH 'cd test/GameOfRope/dirContestants ; java clientSide.main.ClientGameOfRopeContestant '$GRH' '$GRP' '$PGH' '$PGP' '$CBH' '$CBP' stat'
