#!/bin/bash

pid=""
ppid=""
name=""
stat=""

get_pid()
{
G=$1
line=$(cat /proc/$G/status | grep -w Pid: | cut -d ':' -f 2 | awk '{ print "pid="$1}')
eval $line
}
get_ppid()
{
G=$1
line1=$(cat /proc/$G/status | grep PPid: | cut -d ':' -f 2 | awk '{ print "ppid="$1}')
eval $line1
}
get_name()
{
G=$1
line2=$(cat /proc/$G/status | grep Name: | cut -d ':' -f 2 | awk '{ print "name="$1}')
eval $line2
}
get_stat(){
G=$1
line3=$(cat /proc/$G/status | grep State: | cut -d ':' -f 2 | awk '{ print "stat="$1}')
eval $line3
}



echo -e "PID\tPPID\tNAME\t\tSTAT"
for G in `ls /proc | grep '[0-9]'`
do 
get_pid $G
get_ppid $G
get_name $G
get_stat $G
echo -e  "$pid\t$ppid\t$name\t\t$stat"
done
	
	
