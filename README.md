
# Wienerluft
This project is for educational purposes. The aim is to demonstrate how to build an end-to-end data integration pipeline incl. a public web facing page with close to zero costs.
As a side effect it allows to monitor & analyze Vienna's air quality metrics.  

The data source of this project is provided by Stadt Wien – [data.wien.gv.at](https://data.wien.gv.at/)

**See deployed example:** [www.wienerluft.at](http://www.wienerluft.at) 

### Disclaimer

This is a private project for pure educational purposes. Szabolcs Rozsnyai is an employee Google. 
The opinions stated here are my own, not those of Google.


## Architecture
![Architecture](resources/architecture.png)

## Cost of running on GCP

## Installation
### Resources which will be used
* Cloud Scheduler
* Cloud Storage
* Pub/Sub
* BigQuery 
* Cloud Functions
* Cloud Build


### Preparation
Set `PROJECTID` in `config.sh` to desired GCP project-id.

**Run initialization** (only required once per project) 

This will enable all required services 
```
./init.sh
```

### Run installation scripts to provision required resources
```
cd resources
./create.sh
``` 
### Deprovision resources
```
./clean.sh
```


