#!/bin/bash

#Check if variable exists
if [ -z "$GOOGLE_OAUTH2_CLIENT_ID_B64ENC" ]; then
    echo "GOOGLE_OAUTH2_CLIENT_ID_B64ENC is not set."
    exit 1
fi
if [ -z "$GOOGLE_OAUTH2_CLIENT_SECRET_B64ENC" ]; then
    echo "GOOGLE_OAUTH2_CLIENT_SECRET_B64ENC is not set."
    exit 1
fi


#Set values
export GOOGLE_OAUTH2_CLIENT_ID=$GOOGLE_OAUTH2_CLIENT_ID_B64ENC
export GOOGLE_OAUTH2_CLIENT_SECRET=$GOOGLE_OAUTH2_CLIENT_SECRET_B64ENC

#Run the application
java -Djava.security.egd=file:/dev/./urandom -jar /app/shopper-buddy.jar

status=$?
exit $status
