#!/bin/sh
# This is a script file to check if the Rest Proxy, Wiremock, Schema Registry have started running before executing the end to end test cases
# This script file is called in run-demo.sh after the execution of docker-compose up command
#Check if the Rest Proxy, Schema Registry, Wiremock are running
echo "Checking if the Rest Proxy, Schema Registry, Wiremock, SUT are running....."
#Setting initial status for Rest proxy, Schema Registry, Wiremock calls
restProxyCallStatus="checking"
schemaRegistryCallStatus="checking"
wiremockCallStatus="checking"
sutCallStatus="checking"
#Setting time interval to check within how many seconds testing needs to be done
timeInterval=30
#Setting expiration time
expirationTime=$(( `date +%s` + timeInterval ))
#Entering into a loop until the calls to Rest proxy, Schema registry, Wiremock are successful
while [ "$restProxyCallStatus" != "success" ] || [ "$schemaRegistryCallStatus" != "success" ] || [ "$wiremockCallStatus" != "success" ] || [ "$sutCallStatus" != "success" ];
do
#looping is done until current time is less than the expiration time
  if [[ `date +%s` -gt expirationTime ]]; then
    echo "Testing Rest proxy, Schema Registry, Wiremock calls failed......."
    break
  else
#Testing Rest proxy calls
      if [[ "$restProxyCallStatus" != "success" ]];   then
        if curl --silent 'http://localhost:8082/topics' > /dev/null
          then
            echo "Rest Proxy started running"
            restProxyCallStatus="success"
        fi
      fi
#Testing Schema Registry calls
      if [[ "$schemaRegistryCallStatus" != "success" ]];   then
        if curl --silent 'http://localhost:8081/subjects' > /dev/null
          then
            echo "Schema Registry started running"
            schemaRegistryCallStatus="success"
        fi
      fi
#Testing Wiremock calls
      if [[ "$wiremockCallStatus" != "success" ]];   then
        if curl --silent 'http://localhost:8080/app/purchase-orders/601945b2-213c-49f2-bfa0-1aa14aabed95' > /dev/null
          then
            echo "Wiremock started running"
            wiremockCallStatus="success"
        fi
      fi
#Testing System under test (SUT) calls
       if [[ "$sutCallStatus" != "success" ]];   then
        if curl --silent 'http://localhost:8080/actuator/health' > /dev/null
          then
            echo "System under test (SUT) started running"
            sutCallStatus="success"
        fi
      fi
    fi
done
