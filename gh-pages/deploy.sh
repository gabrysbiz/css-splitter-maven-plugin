(
set -x

cd $(dirname "${BASH_SOURCE[0]}")
cd ..

cd target/gh-pages
git remote add origin-pages https://$GITHUB_TOKEN@github.com/$GITHUB_REPO.git
git push -q -u origin-pages gh-pages
)
