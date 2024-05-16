source config.sh

pkill xterm

sshpass -f password ssh $USER@$RMH 'kill $(lsof -t -i:'$RMP') &'
sshpass -f password ssh $USER@$RRH 'kill $(lsof -t -i:'$RRP') &'
sshpass -f password ssh $USER@$GRH 'kill $(lsof -t -i:'$GRP') &'
sshpass -f password ssh $USER@$RSH 'kill $(lsof -t -i:'$RSP') &'
sshpass -f password ssh $USER@$PGH 'kill $(lsof -t -i:'$PGP') &'
sshpass -f password ssh $USER@$CBH 'kill $(lsof -t -i:'$CBP') &'
