#!/bin/bash
echo "Cleaning up ProjectID: $1";
bq rm --dataset $1:wienerluft

echo "Delete topic 'wienerlufttriggertopic': $1";
gcloud pubsub topics delete wienerlufttriggertopic --project=$1