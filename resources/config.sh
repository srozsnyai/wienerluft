#!/bin/bash

# Change
echo "### CONFIG ###"
PROJECTID=xxx
gcloud config set project $PROJECTID

# Change only if you want
WORKFLOWSA=sawienerluft2

echo "# Using ProjectID: $PROJECTID"
echo "# Using ServiceAccount: $WORKFLOWSA"