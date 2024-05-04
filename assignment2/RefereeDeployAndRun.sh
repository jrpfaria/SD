echo "Transfering data to the referee node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirReferee.zip sd102@l040101-ws07.ua.pt:test/GameOfRope
echo "Decompressing data sent to the referee node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'cd test/GameOfRope ; unzip -uq dirReferee.zip'
echo "Executing program at the referee node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'cd test/GameOfRope/dirReferee ; java clientSide.main.ClientGameOfRopeReferee l040101-ws01.ua.pt 22110 l040102-ws02.ua.pt 22110 l040104-ws05.ua.pt 22110 l040104-ws06.ua.pt 22110'
