#!/usr/local/bin/bash
# Script to test our Explorer on different maps
mvn -q exec:java -Djava.awt.headless=true -Dexec.args="$*"
tmp=${1%%.*}
map=${tmp##*/}
echo $map
mkdir -p scripts/results/reports/$9

cat logs/report.log > scripts/results/reports/$9/$map''_report.log