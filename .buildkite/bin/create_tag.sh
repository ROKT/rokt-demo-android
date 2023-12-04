#!/bin/bash

set -eu

# $1 version

git config user.email "buildkite@rokt.com"
git config user.name "Buildkite"

git tag -d "v$1" || true
git add app/build.gradle
git add buildSrc/src/main/java/dependencies.kt
git commit -m "v$1"
git tag -a "v$1" -m "Automated release v$1"
git push origin "v$1"