#!/bin/bash

echo "using ProjectID: $1";
echo "Creating dataset: wienerluft"
bq --location=europe-west1 mk \
--dataset $1:wienerluft\

echo "Creating table: stations"
# Create Stations
## stations reference table
bq load \
--project_id=$1 \
--source_format=CSV \
$1:wienerluft.stations \
./stations.csv \
stationId:STRING,stationName:STRING

echo "Creating table: raw"
## raw table for readings
bq mk $1:wienerluft.raw \
stationId:STRING,dateTime:STRING,windSpeed:STRING,\
windDirection:STRING,rf:STRING,no2:STRING,\
nox:STRING,pm10:STRING,pm10_24:STRING,pm25:STRING,\
pm25_24:STRING,o3:STRING,o3_24:STRING,S02:STRING,\
CO:STRING,CO_24:STRING


