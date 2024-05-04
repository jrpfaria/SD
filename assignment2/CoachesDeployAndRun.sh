echo "Transfering data to the coaches node."
sshpass -f password ssh sd102@l040101-ws08.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws08.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirCoaches.zip sd102@l040101-ws08.ua.pt:test/GameOfRope
echo "Decompressing data sent to the coaches node."
sshpass -f password ssh sd102@l040101-ws08.ua.pt 'cd test/GameOfRope ; unzip -uq dirCoaches.zip'
echo "Executing program at the coaches node."
sshpass -f password ssh sd102@l040101-ws08.ua.pt 'cd test/GameOfRope/dirCoaches ; java clientSide.main.ClientGameOfRopeCoachd l040101-ws01.ua.pt 22110 l040102-ws02.ua.pt 22110 l040104-ws05.ua.pt 22110 l040104-ws06.ua.pt 22110'
