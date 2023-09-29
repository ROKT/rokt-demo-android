#!/bin/bash

set -eu

# $1 New app version code
# $2 New app version name
# $3 Android sdk version name
# $4 app build.gradle file location
# $5 dependencies file location

# This will break if the indentation changes in build.gradle

perl -pi -e "s/(?<=versionCode findProperty\(\"version_code\"\) as Integer \?: )(.*)(?=\n)/$1/g" "$4"
perl -pi -e "s/(?<=versionName findProperty\(\"version_name\"\) \?: \")(.*)(?=\")/$2/g" "$4"

perl -pi -e "s/(?<=com.rokt:roktsdk:)(.*)(?=\")/$3/g" "$5"