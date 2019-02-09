#!/usr/local/bin/bash
# Script to compute the results log

cd scripts/results/reports

respath=../../results.log
echo "Simulation report :"> $respath
echo >> $respath

# Adds all the map numbers in an array
i=0
for f in *.log; do
	num=${f:4:2}
	array[$i]=$num
	((i++))
done

# Remove duplicates in map numbers
sorted=($(echo "${array[@]}" | tr ' ' '\n' | sort -u | tr '\n' ' '))

# Compute completed goals vs total goals for each group of maps (with same number)
for j in "${sorted[@]}"; do
	goals_done=0; goals_total=0; budget_left=0; budget_total=0
	for f in map_$j*.log
	do
	    # Find goal amount
	    goals=$(awk '$3 == "Goals" {print $NF}' $f)
		IFS='/' read -ra values <<< $goals
		goals_done=$((goals_done + values[0]))
		goals_total=$((goals_total + values[1]))

        # Find budget
        budget=$(awk '$3 == "Budget" {print $NF}' $f)
		IFS='/' read -ra values <<< $budget
		budget_left=$((budget_left + values[0]))
		budget_total=$((budget_total + values[1]))
	done

	# Add to the total for average
	all_goals_done=$((all_goals_done + goals_done)); all_goals=$((all_goals + goals_total))
	all_budget_left=$((all_budget_left + budget_left)); all_budget=$((all_budget + budget_total))

	# echo goals and budget
	echo map_$j : >> $respath
	echo - Goals : $((100*goals_done/goals_total))% $goals_done/$goals_total >> $respath
	echo - Budget : $((100*budget_left/budget_total))% $budget_left/$budget_total >> $respath
	echo >> $respath
done

# Prints the number of islands explored
total_islands=$(ls | wc -l)
echo >> $respath
echo "Results for" $total_islands "island(s) :" >> $respath

# Prints goals average
echo $((100*all_goals_done/all_goals))% "goals completed," $all_goals_done/$all_goals >> $respath

#Prints budget average
echo $((100*all_budget_left/all_budget))% "budget left," $all_budget_left/$all_budget>> $respath