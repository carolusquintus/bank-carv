#!/bin/zsh

docker-compose down
docker-compose up -d

sleep 2s

# Install tc utility as root
docker exec -it --user root bank-account-db apt-get update
docker exec -it --user root bank-account-db apt-get install -y iproute2

# apply latency command
docker exec -it --user root bank-account-db tc qdisc add dev eth0 root netem delay 1000ms

# change latency
#docker exec -it --user root bank-account-db tc qdisc change dev eth0 root netem delay 2500ms

# remove latency
#docker exec -it --user root bank-account-db tc qdisc del dev eth0 root netem
