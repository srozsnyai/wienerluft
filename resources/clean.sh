#!/bin/bash

. ./config.sh

echo "# Delete Cloud Scheduler"
yes | gcloud scheduler jobs delete wienerlufttrigger

echo "# Delete Cloud Function"
yes | gcloud functions delete wienerluftcf \
--region=europe-west1

echo "# Deleting Table: ${PROJECTID}:wienerluft.raw";
bq rm -t=true -f=true ${PROJECTID}:wienerluft.raw

echo "# Deleting Table: ${PROJECTID}:wienerluft.stations";
bq rm -t=true -f=true ${PROJECTID}:wienerluft.stations

echo "# Deleting Dataset: ${PROJECTID}:wienerluft.raw";
bq rm -d=true -f=true ${PROJECTID}:wienerluft

echo "# Delete topic 'wienerlufttriggertopic': ${PROJECTID}";
gcloud pubsub topics delete wienerlufttriggertopic

#echo "# Delete zip"
#rm -f ../target/wienerluftcode.zip

echo "# Delete GCS bucket "
yes | gcloud alpha storage rm --recursive gs://${PROJECTID}wienerluftstaging


