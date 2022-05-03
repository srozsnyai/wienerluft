#!/bin/bash
echo "# Cleaning up ProjectID: $1";
gcloud config set project $1

echo "# Deleting Table: $1:wienerluft.raw";
bq rm -t=true -f=true $1:wienerluft.raw

echo "# Deleting Table: $1:wienerluft.stations";
bq rm -t=true -f=true $1:wienerluft.stations

echo "# Deleting Dataset: $1:wienerluft.raw";
bq rm -d=true -f=true $1:wienerluft

echo "# Delete topic 'wienerlufttriggertopic': $1";
gcloud pubsub topics delete wienerlufttriggertopic

#echo "# Delete zip"
#rm -f ../target/wienerluftcode.zip

echo "# Delete GCS bucket "
yes | gcloud alpha storage rm --recursive gs://$1wienerluftstaging

echo "# Delete Cloud Function"
yes | gcloud functions delete wienerluftcf \
--region=europe-west1
