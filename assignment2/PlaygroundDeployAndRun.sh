echo "Transfering data to the playground node."
sshpass -f password ssh sd102@l040101-ws05.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws05.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirPlayground.zip sd102@l040101-ws05.ua.pt:test/GameOfRope
echo "Decompressing data sent to the playground node."
sshpass -f password ssh sd102@l040101-ws05.ua.pt 'cd test/GameOfRope ; unzip -uq dirPlayground.zip'
echo "Executing program at the playground node."
sshpass -f password ssh sd102@l040101-ws05.ua.pt 'cd test/GameOfRope/dirPlayground ; java serverSide.main.ServerGameOfRopePlayground 22110 l040101-ws01.ua.pt 22110'
