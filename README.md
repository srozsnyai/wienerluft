# Wienerluft

#### Installation
##### Required Resources
* Cloud Scheduler
* Cloud Storage
* Pub/Sub
* Cloud Functions
* BigQuery 

##### Preparation
Set `PROJECTID` in `config.sh` to desired GCP project-id.

##### Run installation scripts to provision required resources
```
cd resources
./config.sh
./create.sh
``` 
##### Deprovision resources
```
./clean.sh
```


