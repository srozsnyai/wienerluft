#!/bin/bash

# Change
echo "### CONFIG ###"
PROJECTID=xxx

gcloud config set project ${PROJECTID}
echo "# Using ProjectID: ${PROJECTID}"
