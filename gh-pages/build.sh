(
set -x

cd $(dirname "${BASH_SOURCE[0]}")
cd ..

rm -rf target/gh-pages
mkdir target/gh-pages
cd target/gh-pages
git clone -q --single-branch -b gh-pages https://github.com/$GITHUB_REPO.git .

for entry in $(ls)
do
    if [ -f $entry ]; then
        rm $entry
    fi
done
if [ -d LATEST ]; then
    rm -r LATEST
fi

cp ../../gh-pages/files/* .
cp ../../src/main/resources/license.txt .
if [ -n "$GITHUB_PAGES_DOMAIN" ]; then
    echo -n "$GITHUB_PAGES_DOMAIN" > CNAME
fi
mkdir LATEST
cp -r ../site/* LATEST/

cd ../../
mvn -B -e help:evaluate -Dexpression=project.version -Doutput=target/gh-pages-project-version.txt
version=$(cat target/gh-pages-project-version.txt)
cd target/gh-pages
if [[ $version != *-SNAPSHOT ]] && [ ! -d $version ]; then
    mkdir $version
    cp -r ../site/* $version/
fi

git config user.name "Travis CI"
git config user.email "travis@travis-ci.org"
git add .
if [[ $version == *-SNAPSHOT ]]; then
    git commit -m "Update LATEST documentation"
else
    git commit -m "Add $version documentation"
fi
)
