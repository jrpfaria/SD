echo "Transfering data to the contestants bench node."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirContestantsBench.zip sd102@l040101-ws06.ua.pt:test/GameOfRope
echo "Decompressing data sent to the contestants bench node."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'cd test/GameOfRope ; unzip -uq dirContestantsBench.zip'
echo "Executing program at the contestants bench node."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'cd test/GameOfRope/dirContestantsBench ; java serverSide.main.ServerGameOfRopeContestantsBench 22110 l040101-ws01.ua.pt 22110'
