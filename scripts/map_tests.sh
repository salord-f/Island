#!/usr/local/bin/bash
# Script to test our Explorer on different maps

if [ -z "$1" ]
  then
    echo "Please supply argument f (full) or l (light) or r (resources)"
    exit 1
fi

CURRENT_PATH=$(pwd)

cd $(dirname $0)/..;

mvn clean install;

mkdir -p logs
mkdir -p scripts/results
mkdir -p scripts/results/full_logs
mkdir -p scripts/results/reports


if [ $1 == "l" ]; then 
	bash scripts/subscripts/run_maps_light.sh
	bash scripts/subscripts/summary_results.sh
fi

if [ $1 == "f" ]; then 
	bash scripts/subscripts/run_maps_full.sh
	bash scripts/subscripts/summary_results.sh
fi

if [ $1 == "r" ]; then
	bash scripts/subscripts/run_maps_resources.sh
fi

cd $CURRENT_PATH;