source config.sh

for HOST in l040101-ws01.ua.pt l040101-ws05.ua.pt l040101-ws06.ua.pt l040101-ws07.ua.pt l040101-ws09.ua.pt l040101-ws10.ua.pt
do
    echo $HOST
    for PORT in {22110..22119}
    do
        sshpass -f password ssh $USER@$HOST 'kill $(lsof -t -i:'$PORT') &'
    done
done
