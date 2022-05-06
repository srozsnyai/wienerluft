#!/bin/bash
# Tools required - gcloud, gsutil, zip
# glcoud needs to be authenticated - i.e. gcloud auth login

# load config
#WORKFLOWSA=sawienerluft2
#echo "# Using ProjectID: ${PROJECTID}"
#gcloud config set project ${PROJECTID}
. ./config.sh

echo "# Creating dataset: wienerluft"
bq --location=europe-west1 mk \
--dataset ${PROJECTID}:wienerluft\

echo "# Creating table: stations"
# Create Stations
## stations reference table
bq load \
--project_id=${PROJECTID} \
--source_format=CSV \
${PROJECTID}:wienerluft.stations \
./stations.csv \
stationId:STRING,stationName:STRING

echo "# Creating table: raw"
## raw table for readings
bq mk ${PROJECTID}:wienerluft.raw \
stationId:STRING,dateTime:STRING,windSpeed:STRING,\
windDirection:STRING,rf:STRING,no2:STRING,\
nox:STRING,pm10:STRING,pm10_24:STRING,pm25:STRING,\
pm25_24:STRING,o3:STRING,o3_24:STRING,S02:STRING,\
CO:STRING,CO_24:STRING

echo "# Creating topic 'wienerlufttriggertopic': ${PROJECTID}";
gcloud pubsub topics create wienerlufttriggertopic --project=${PROJECTID}

#echo "# zipping code repo"
#zip -r ../target/wienerluftcode.zip ../ -x "*/.*"
#zip -rj ../target/wienerluftcode.zip ../../wienerluft

echo "# Creating stage bucket for Cloud Functions and moving code ZIP to it";
gsutil mb -p ${PROJECTID} -c STANDARD -l EUROPE-WEST1 -b on gs://${PROJECTID}wienerluftstaging
gsutil cp ../target/wienerluftcode.zip gs://${PROJECTID}wienerluftstaging

echo "# Creating cloudfunction ";
gcloud functions deploy wienerluftcf \
--runtime=java11 \
--trigger-event=providers/cloud.pubsub/eventTypes/topic.publish \
--trigger-resource=wienerlufttriggertopic \
--entry-point=at.wienerluft.TriggerPubSub \
--max-instances=1 \
--region=europe-west1 \
--set-env-vars=PROJECTID=${PROJECTID},DATASET=wienerluft,BQTABLE=raw \
--timeout=60s \
--stage-bucket=gs://${PROJECTID}wienerluftstaging \
--source=gs://${PROJECTID}wienerluftstaging/wienerluftcode.zip \
--memory=128MB

echo "# Creating Cloud Scheduler to trigger Workflow"
gcloud scheduler jobs create pubsub wienerlufttrigger \
--schedule="*/15 * * * *" \
--topic=wienerlufttriggertopic \
--message-body="Hello"
