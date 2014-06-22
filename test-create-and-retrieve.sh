#!/bin/bash

API_VERSION=$1

resp=`curl -s -X POST -u lukasz:budnik -H "Content-Type: application/json" -d '{"description": "gogo"}' http://localhost:8080/api/$API_VERSION/item`

if [ "$resp" == "There was an internal server error." ]
then
    echo $resp
    exit
fi

ref=`echo $resp | awk 'match($0, /([0-9]+)/) {print substr($0,RSTART,RLENGTH)}' `
item=`curl -s -u lukasz:budnik http://localhost:8080/api/$API_VERSION/item/$ref`


