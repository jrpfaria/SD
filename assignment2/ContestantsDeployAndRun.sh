echo "Transfering data to the contestants node."
sshpass -f password ssh sd102@l040101-ws09.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws09.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirContestants.zip sd102@l040101-ws09.ua.pt:test/GameOfRope
echo "Decompressing data sent to the contestants node."
sshpass -f password ssh sd102@l040101-ws09.ua.pt 'cd test/GameOfRope ; unzip -uq dirContestants.zip'
echo "Executing program at the contestants node."
sshpass -f password ssh sd102@l040101-ws09.ua.pt 'cd test/GameOfRope/dirContestants ; java clientSide.main.ClientGameOfRopeContestant l040101-ws01.ua.pt 22110 l040101-ws02.ua.pt 22110 l040101-ws05.ua.pt 22110 l040101-ws06.ua.pt 22110'
