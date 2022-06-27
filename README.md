
# Wiener Luft
This project is for educational purposes. The aim is to demonstrate how to build an end-to-end data integration pipeline incl. a public web facing page with close to zero costs.
As a side effect it allows to monitor & analyze Vienna's air quality metrics.  

The data source of this project is provided by Stadt Wien – [data.wien.gv.at](https://data.wien.gv.at/)

**See deployed example:** [www.wienerluft.at](http://www.wienerluft.at) 

### Disclaimer

This is a private project for  educational purposes. Szabolcs Rozsnyai is an employee Google. 
The opinions stated here are my own, not those of Google.


## Architecture
![Architecture](resources/architecture.png)

#### Data Pipeline
The Cloud Scheduler emits every 15min a a pub/sub message to trigger the execution of a Cloud Function. The Cloud Function runs a Java-based application which calls the Stadt Wien API. This API returns the current readings from all the AQ stations. The cloud function parses the returned message and writes them into a BQ table in their raw format (i.e. tabular but untyped). 

#### Storage & Analytics
A BQ view is used to type the individual attributes that have been previously stored as simple string values.
  
```
SELECT
       stationId,
       PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:')) as dateTime,
       FORMAT_DATE('%A', PARSE_DATE('%d.%m.%Y', SUBSTR(dateTime,0,10))) AS dayOfWeek,
       FORMAT_DATE('%u', PARSE_DATE('%d.%m.%Y', SUBSTR(dateTime,0,10))) AS dayOfWeekAsNumber,
       EXTRACT(HOUR FROM PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:'))) as hourOfDay,
       EXTRACT(YEAR FROM PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:'))) as year,
       EXTRACT(MONTH FROM PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:'))) as month,
       EXTRACT(DAY FROM PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:'))) as day,
       EXTRACT(WEEK FROM PARSE_DATETIME('%d.%m.%Y, %H:%M',  REPLACE(RTRIM(LTRIM(dateTime)),'24:', '00:'))) as week,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(windSpeed)),',','.') AS NUMERIC) as windSpeed,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(windDirection)),',','.') AS NUMERIC) as windDirection,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(rf)),',','.') AS NUMERIC) as rf,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(no2)),',','.') AS NUMERIC) as no2,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(nox)),',','.') AS NUMERIC) as nox,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(pm10)),',','.') AS NUMERIC) as pm10,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(pm10_24)),',','.') AS NUMERIC) as pm10_24,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(pm25)),',','.') AS NUMERIC) as pm25,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(pm25_24)),',','.') AS NUMERIC) as pm25_24,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(o3)),',','.') AS NUMERIC) as o3,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(o3_24)),',','.') AS NUMERIC) as o3_24,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(S02)),',','.') AS NUMERIC) as S02,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(CO)),',','.') AS NUMERIC) as CO,
       SAFE_CAST(REPLACE(RTRIM(LTRIM(CO_24)),',','.') AS NUMERIC) as CO_24

       FROM PROJECTID.wienerluft.raw
       ORDER BY dateTime DESC 
  ```


The view is the source table for the DataStudio report which is embedded in a static webpage. 
This static webpage is served from a GCP bucket.  

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


