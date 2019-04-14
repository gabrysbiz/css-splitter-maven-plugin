#!/usr/bin/env bash

(
cd $(dirname "${BASH_SOURCE[0]}")
cd ..

rm -rf target/gh-pages
mkdir target/gh-pages
cd target/gh-pages
git clone -q --single-branch -b gh-pages https://github.com/gabrysbiz/css-splitter-maven-plugin.git .

# clear all files which are not associated with any release
for entry in $(ls)
do
    if ! [[ $entry =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
        rm -r $entry
    fi
done

# copy web files and generated site to X.Y.Z for releases (only if does not exist) or LATEST for SNAPSHOTS
cp -r ../../gh-pages/static-files/* .
cp ../../src/main/resources/license.txt .
cd ../../
mvn -B -e help:evaluate -Dexpression=project.version -Doutput=target/gh-pages-project-version.txt > /dev/null
version=$(cat target/gh-pages-project-version.txt)
cd target/gh-pages
if [[ $version != *-SNAPSHOT ]] && [ ! -d $version ]; then
    mkdir $version
    cp -r ../site/* $version/
elif [[ $version == *-SNAPSHOT ]]; then
    mkdir LATEST
    cp -r ../site/* LATEST/
fi

# generate index.html file
releaseVersion=$version
latestHeaderLink=''
latestVersionDiv=''
if [[ $version == *-SNAPSHOT ]]; then
    releaseVersion=$(../../gh-pages/get-latest-release-version.sh .)
    latestHeaderLink="
            <a href=\"LATEST/\" class=\"btn\" rel=\"nofollow\">LATEST ($version) docs</a>"
    latestVersionDiv="
            <p>
                <a href=\"LATEST/\" rel=\"nofollow\">$version docs</a> - the newest version under development.
            </p>"
fi
releasesLinks=''
for item in $(../../gh-pages/get-release-versions.sh .)
do
    if [ -n "$releasesLinks" ]; then
        releasesLinks="$releasesLinks,
                "
    fi
    releasesLinks="$releasesLinks<a href=\"$item/\">$item</a>"
done

indexHtml=$(cat ../../gh-pages/index.html.template)
indexHtml=${indexHtml//%RELEASE_VERSION%/$releaseVersion}
indexHtml=${indexHtml//%LATEST_VERSION%/$version}
indexHtml=${indexHtml//%LATEST_VERSION_HEADER_LINK%/$latestHeaderLink}
indexHtml=${indexHtml//%LATEST_VERSION_DIV%/$latestVersionDiv}
indexHtml=${indexHtml//%AVAILABLE_RELEASES_LIST%/$releasesLinks}
echo -n "$indexHtml" > index.html

# save all changes
git add .
message=''
if [[ $version == *-SNAPSHOT ]]; then
    message="Update LATEST documentation"
else
    message="Add $version documentation"
fi
git -c 'user.name=Travis CI' -c 'user.email=travis@travis-ci.org' commit -m "$message"
)
