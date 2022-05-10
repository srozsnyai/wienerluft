SELECT a.*, b.stationname FROM
       (
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
       ) a,
       PROJECTID.wienerluft.stations b
WHERE b.stationId = a.stationId