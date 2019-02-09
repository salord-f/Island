#!/usr/local/bin/bash
# Script to test our Explorer on different maps

mvn -q exec:java -Djava.awt.headless=true -Dexec.args="$*"
tmp=${1%%.*}
map=${tmp##*/}
echo $map
cat logs/explorer.log > scripts/results/full_logs/$map'_('$2,$3')_'$4.log
cat logs/report.log > scripts/results/reports/$map'_('$2,$3')_'$4_report.log