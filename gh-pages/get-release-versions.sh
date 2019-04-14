#!/usr/bin/env bash

(
versions=''
for item in $(ls $1);
do
    if [ ! -d $1/$item ]; then
        continue
    fi
    if [[ $item =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]; then
        major="---${BASH_REMATCH[1]}"
        minor="---${BASH_REMATCH[2]}"
        patch="---${BASH_REMATCH[3]}"
        versions="$versions ${major: -3}.${minor: -3}.${patch: -3}"
    fi
done

sorted=$(echo $versions | tr ' ' '\n' | sort -r)
echo ${sorted//-/}
)
