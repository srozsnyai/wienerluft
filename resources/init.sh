#!/bin/bash

. ./config.sh

echo "Enabling services"
gcloud services enable bigquery.googleapis.com
gcloud services enable cloudscheduler.googleapis.com
gcloud services enable cloudfunctions.googleapis.com
gcloud services enable pubsub.googleapis.com


echo "Create Service Account & Permissions"
gcloud iam service-accounts create ${WORKFLOWSA} \
    --description="sawienerlufttrigger" \
    --display-name="sawienerlufttrigger"

gcloud projects add-iam-policy-binding ${PROJECTID} \
    --member="serviceAccount:${WORKFLOWSA}@${PROJECTID}.iam.gserviceaccount.com" \
    --role="roles/pubsub.publisher"

gcloud projects add-iam-policy-binding ${PROJECTID} \
   --member "serviceAccount:${WORKFLOWSA}@${PROJECTID}.iam.gserviceaccount.com" \
   --role "roles/logging.logWriter"

gcloud projects add-iam-policy-binding ${PROJECTID} \
    --member="serviceAccount:${WORKFLOWSA}@${PROJECTID}.iam.gserviceaccount.com" \
    --role="roles/workflows.invoker"