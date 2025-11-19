# PcRepairBatch

## Description
Batch service that creates a CSV with the users, with spring boot we scan all beans and the CommandLineRunner executes the job.

After execution in output source should appear the person.csv

## Set up
For this batch service to work you need to previously have deployed the database (See docker-compose in docker-jpd folder within https://github.com/JosepeDevs/PcRepairPersonData)
Once that is deployed and the database is up, executing this will produce the csv file.

## Configurability

This is configurable in application.propeties changing:

batch.export.output-directory=output
batch.export.output-file=persons.csv
batch.export.delimiter=;

you will be able to change where the csv is created, the name of the file and the delimiter.

