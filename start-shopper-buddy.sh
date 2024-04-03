#!/bin/bash

#Check if variable exists
if [ -z "$GOOGLE_OAUTH2_CLIENT_ID" ]; then
    echo "GOOGLE_OAUTH2_CLIENT_ID is not set."
    exit 1
fi
if [ -z "$GOOGLE_OAUTH2_CLIENT_SECRET" ]; then
    echo "GOOGLE_OAUTH2_CLIENT_SECRET is not set."
    exit 1
fi

#Run the application
java -Djava.security.egd=file:/dev/./urandom -jar /app/shopper-buddy.jar

status=$?
exit $status
