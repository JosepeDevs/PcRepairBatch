# PcRepairBatch

## Extensive documentation in https://app.devin.ai/wiki/JosepeDevs/PcRepairBatch

## Description

Batch service that creates in a scheduled fashion (configurable with cron) a CSV or JSON file  (configurable in application.properties) with the users.

After execution in "output" folder (configurable) should appear the file.

## Set up

For this batch service to work you need to previously have deployed the database (See docker-compose in docker-jpd folder within https://github.com/JosepeDevs/PcRepairPersonData)
Once that is deployed and the database is up, executing this will produce the configured file.

## Configurability

The scheduled application can be configurable in application.properties changing:

```properties
batch.export.output-directory=output
batch.export.output-file=persons.txt
batch.export.delimiter=;
batch.export.include-headers=true
batch.export.chunk-size=500
batch.export.export-format=csv
batch.export.cron=0 0/1 * * * *
```

### Directory
Folder (existent or not) below project-root that will be created and will contain the file

### file
File name to be given, bear in mind if you want a specific extension you must specify it, p.e.: people.json or 01012025.txt

### Delimiter
Character or characters that will separate (only applies for csv files)

### Include-headers
Boolean (true/false) that determines if the first row should be the column's name or start directly with the data. Only applies for csv.

### Chunk-size
you will be able to change where the csv is created, the name of the file and the delimiter.

### Export-format
Available options are json or csv (ignores cases so Json JSON and json will produce jsons). Any other option will throw an error.

### cron
Supports Quartz cron format: 

second minute hour day-of-month month day-of-week [year optional]

| Position | Field         | Allowed Values                   | Notes                               |
|----------|---------------|-----------------------------------|--------------------------------------|
| 1        | Seconds       | 0?59                              |                                      |
| 2        | Minutes       | 0?59                              |                                      |
| 3        | Hours         | 0?23                              |                                      |
| 4        | Day of Month  | 1?31                              | `?` allowed (no specific value)       |
| 5        | Month         | 1?12 or JAN?DEC                   |                                      |
| 6        | Day of Week   | 0?7 or SUN?SAT                    | 0 and 7 both mean Sunday; `?` allowed |
| 7 (opt.) | Year          | empty or 1970?2099                | Optional field                       |

examples:

Every 10 min during working hours:
```cron
0 0/10 8-17 * * MON-FRI
```

Every Monday at 14:30 in 2026:
```cron
0 30 14 * * MON ? 2026
```

Every 5 seconds between range 0-30 and 50, during 3?5 PM, on any day-of-month, in January to March, on the 2nd Monday or the last weekday of the month, every year:
```cron
0-30/5,50 15-17/1 ? JAN-MAR MON#2,LW *
```

## On demand configuration via REST
This application exposes endpoint /run-job that accepts various values that will be used for a sinlge run, isntead of the scheduled configured
Check the OAS yaml for more information. Found in the API GW repository of this project: