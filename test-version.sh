#!/bin/bash

API_VERSION=$1

NO_OF_REQUESTS=1000

start=`date +%s`

for (( i=1; i<=$NO_OF_REQUESTS; i++ ))
do
   ./test-create-and-retrieve.sh $API_VERSION &
done

wait

end=`date +%s`

let "time=$end-$start"

echo "$API_VERSION total time: $time seconds"
