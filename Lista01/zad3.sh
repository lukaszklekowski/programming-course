#!/bin/bash

sitename=$1
time=$2
lynx -dump $sitename > site1.txt

while true; do

sleep $2
lynx -dump $sitename > site2.txt
bool=(diff -q site1.txt site2.txt)

if [ $bool ]
then
	date -u >> history.txt
	diff site1.txt site2.txt >> history.txt
	echo "Changes have occured"
fi

cat site2.txt > site1.txt

done
