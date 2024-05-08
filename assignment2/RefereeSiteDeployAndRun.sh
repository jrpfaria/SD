echo "Transfering data to the referee site node."
sshpass -f password ssh sd102@l040101-ws10.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws10.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirRefereeSite.zip sd102@l040101-ws10.ua.pt:test/GameOfRope
echo "Decompressing data sent to the referee site node."
sshpass -f password ssh sd102@l040101-ws10.ua.pt 'cd test/GameOfRope ; unzip -uq dirRefereeSite.zip'
echo "Executing program at the referee site node."
sshpass -f password ssh sd102@l040101-ws10.ua.pt 'kill $(lsof -t -i:22110)'
sshpass -f password ssh sd102@l040101-ws10.ua.pt 'cd test/GameOfRope/dirRefereeSite ; java serverSide.main.ServerGameOfRopeRefereeSite 22110 l040101-ws01.ua.pt 22110'
