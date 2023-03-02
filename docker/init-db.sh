#!/bin/bash
set -e

docker-compose up -d

echo waiting for db
until docker-compose exec postgresql psql -Uroot root  -c 'select now();'
   do printf .;  sleep 1; done
echo

echo "Waiting 5 secs for the database to be fully operational"
sleep 5

echo Database is up. Initializing...

# Initialize
docker-compose exec postgresql psql -Uroot root  -c "CREATE DATABASE  order_automation;"
docker-compose exec postgresql psql -Uroot root  -c "CREATE USER shop_automator WITH PASSWORD 'somePassword';"
docker-compose exec postgresql psql -Uroot root  -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shop_automator;"

#setup
#docker-compose exec postgresql psql -Uroot root  -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shop_automator;"


echo Initialization completed successfully
