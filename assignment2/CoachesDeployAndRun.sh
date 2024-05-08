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

echo "Transfering data to the coaches node."
sshpass -f password ssh $USER@$COAH 'mkdir -p test/GameOfRope'
sshpass -f password ssh $USER@$COAH 'rm -rf test/GameOfRope/dirCoaches'
sshpass -f password scp dirCoaches.zip $USER@$COAH:test/GameOfRope
echo "Decompressing data sent to the coaches node."
sshpass -f password ssh $USER@$COAH 'cd test/GameOfRope ; unzip -uq dirCoaches.zip'
echo "Executing program at the coaches node."
sshpass -f password ssh $USER@$COAH 'cd test/GameOfRope/dirCoaches ; java clientSide.main.ClientGameOfRopeCoach '$GRH' '$GRP' '$RSH' '$RSP' '$PGH' '$PGP' '$CBH' '$CBP''
