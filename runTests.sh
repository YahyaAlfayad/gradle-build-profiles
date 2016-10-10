#!/usr/bin/env bash

SCRIPT_PID=$$

function checkExitStatus {

    if [[ $1 != 0 ]]
    then
        echo "exit status does not indicate success. failed at stage $2"
        cd ..
        kill -KILL $SCRIPT_PID
    fi
}

cd build-profiles-plugin

../gradlew clean uploadArchives $@

checkExitStatus $? "uploading plugin binaries"

cd ../gradle-profiles-plugin-consumer

../gradlew createProfiles $@

checkExitStatus $? "creating consumer profiles"

../gradlew clean build $@

checkExitStatus $? "building consumer"