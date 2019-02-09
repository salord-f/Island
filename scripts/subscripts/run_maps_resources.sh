#!/usr/local/bin/bash
# Script to run our Explorer on different maps

rm -rf scripts/results/full_logs/*
rm -rf scripts/results/reports/*

# Island contracts, each corresponds to one resource
mapfile -t contracts < scripts/data/contracts_resources.txt

# Different spawn points
mapfile -t pos < scripts/data/positions.txt


respath=scripts/coeff.log
echo "Coefficients: ">$respath



# Runs all the configurations on all maps
for j in "${contracts[@]}"; do

    coeffTotal=0

	for f in scripts/maps/*.json; do
		bash scripts/subscripts/exec_map_resources.sh $f ${pos[0]} $j
	done;

    echo $j | cut -d " " -f 5
    echo $j | cut -d " " -f 5 >> $respath
    resource=$(echo $j | cut -d " " -f 5)

    i=0
    for g in scripts/results/reports/$resource/*.log; do
            # Find budget
        if [ $(awk '$3 == "Uncompleted" {print $NF}' $g) == "None" ]
            then
                coeff=$(awk '$3 == "Coefficient" {print $NF}' $g)
                IFS='/' read -ra values <<< $coeff
                coeffTotal=$((coeffTotal + coeff))
                ((i++))
        fi;
    done;
        if [ $coeffTotal == 0 ];
            then
                echo 0 >> $respath
            else
                echo $((coeffTotal/i)) >> $respath
        fi;
        echo >> $respath
done;