source config.sh

echo "Killing port $GRP on $GRH"
sshpass -f password ssh $USER@$GRH 'kill $(lsof -t -i:'$GRP')'
echo "Killing port $RSP on $RSH"
sshpass -f password ssh $USER@$RSH 'kill $(lsof -t -i:'$RSP')'
echo "Killing port $PGP on $PGH"
sshpass -f password ssh $USER@$PGH 'kill $(lsof -t -i:'$PGP')'
echo "Killing port $CBP on $CBH"
sshpass -f password ssh $USER@$CBH 'kill $(lsof -t -i:'$CBP')'
