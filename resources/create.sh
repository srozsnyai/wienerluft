#!/bin/bash
# Tools required - gcloud, gsutil, zip
# glcoud needs to be authenticated - i.e. gcloud auth login

echo "# Using ProjectID: $1"
gcloud config set project $1

echo "# Creating dataset: wienerluft"
bq --location=europe-west1 mk \
--dataset $1:wienerluft\

echo "# Creating table: stations"
# Create Stations
## stations reference table
bq load \
--project_id=$1 \
--source_format=CSV \
$1:wienerluft.stations \
./stations.csv \
stationId:STRING,stationName:STRING

echo "# Creating table: raw"
## raw table for readings
bq mk $1:wienerluft.raw \
stationId:STRING,dateTime:STRING,windSpeed:STRING,\
windDirection:STRING,rf:STRING,no2:STRING,\
nox:STRING,pm10:STRING,pm10_24:STRING,pm25:STRING,\
pm25_24:STRING,o3:STRING,o3_24:STRING,S02:STRING,\
CO:STRING,CO_24:STRING


echo "# Creating topic 'wienerlufttriggertopic': $1";
gcloud pubsub topics create wienerlufttriggertopic --project=$1

#echo "# zipping code repo"
#zip -r ../target/wienerluftcode.zip ../ -x "*/.*"
#zip -rj ../target/wienerluftcode.zip ../../wienerluft

echo "# Creating stage bucket for Cloud Functions and moving code ZIP to it";
gsutil mb -p $1 -c STANDARD -l EUROPE-WEST1 -b on gs://$1wienerluftstaging
gsutil cp ../target/wienerluftcode.zip gs://$1wienerluftstaging

echo "# Creating cloudfunction ";
gcloud functions deploy wienerluftcf \
--runtime=java11 \
--trigger-event=providers/cloud.pubsub/eventTypes/topic.publish \
--trigger-resource=wienerlufttriggertopic \
--entry-point=at.wienerluft.TriggerPubSub \
--max-instances=1 \
--region=europe-west1 \
--set-env-vars=PROJECTID=$1,DATASET=wienerluft,BQTABLE=raw \
--timeout=60s \
--stage-bucket=gs://$1wienerluftstaging \
--source=gs://$1wienerluftstaging/wienerluftcode.zip \
--memory=128MB