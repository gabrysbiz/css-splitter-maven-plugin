#!/usr/bin/env bash

(
directory=$(dirname "${BASH_SOURCE[0]}")

for version in $($directory/get-release-versions.sh $1);
do
    echo $version
    exit 0
done
)
