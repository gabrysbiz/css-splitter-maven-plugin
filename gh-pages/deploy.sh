#!/usr/bin/env bash

(
cd $(dirname "${BASH_SOURCE[0]}")
cd ..

credentials=''
if [ -n "$GITHUB_TOKEN" ]; then
    credentials="$GITHUB_TOKEN@"
fi

cd target/gh-pages
git remote add origin-pages https://${credentials}github.com/gabrysbiz/css-splitter-maven-plugin.git
git push -q -u origin-pages gh-pages
)
