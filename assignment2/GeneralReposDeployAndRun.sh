echo "Transfering data to the general repository node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'rm -rf test/GameOfRope/*'
sshpass -f password scp dirGeneralRepos.zip sd102@l040101-ws01.ua.pt:test/GameOfRope
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/GameOfRope ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/GameOfRope/dirGeneralRepos ; java serverSide.main.ServerGameOfRopeGeneralRepos 22110'
echo "Server shutdown."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/GameOfRope/dirGeneralRepos ; less stat'
