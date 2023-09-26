#!/bin/bash

set -eu

# $1 New app version code
# $2 New app version name
# $3 Android sdk version name
# $4 app build.gradle file location
# $5 dependencies file location

# This will break if the indentation changes in build.gradle

perl -pi -e "s/(?<=versionCode )(.*)(?=\n)/$1/g" "$4"
perl -pi -e "s/(?<=versionName \")(.*)(?=\")/$2/g" "$4"

perl -pi -e "s/(?<=com.rokt:roktsdk:)(.*)(?=\")/$3/g" "$5"