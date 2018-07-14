#!/bin/bash
 

web_interface=$1
rec_bytes=""
tr_bytes=""
old_rec_bytes=""
old_tr_bytes=""
time=""
battery=""
 
get_bytes()
{
    l=$(cat /proc/net/dev | grep $web_interface | cut -d ':' -f 2 | awk '{print "rec_bytes="$1, "tr_bytes="$9}')
    eval $l
}
get_time()
{
	l1=$(cat /proc/uptime | cut -d '.' -f 1 |awk '{print "time="$1}')
	eval $l1
}
get_bat()
{
	l2=$(cat /sys/class/power_supply/BAT0/uevent | grep POWER_SUPPLY_CAPACITY= | cut -d '=' -f 2 | awk '{print "battery="$1}')
	eval $l2
}
 
get_velocity()
{
    val=$1    
    old_val=$2
 
    let velocity=$val-$old_val
	echo $velocity;
}
 
get_uptime(){
	seconds=$1
	
	let min=60
	let hours=3600
	let days=86400

	let cdays=$seconds/$days
	let rest=$seconds%$days
	let chours=$rest/$hours
	let rest1=$rest%$hours
	let cmin=$rest1/$min
	let rest2=$rest1%$min

	echo -n "$cdays D, $chours H, $cmin M, $rest2 S"
		
}

get_bytes
old_rec_bytes=$rec_bytes
old_tr_bytes=$tr_bytes
 
let it=1 
 
while true; 
do
    
    get_bytes
    velocity_rec=$(get_velocity $rec_bytes $old_rec_bytes)
    velocity_tr=$(get_velocity $tr_bytes $old_tr_bytes)
	let old_rec=$old_rec+$velocity_rec
	let old_tr=$old_tr+$velocity_tr
	let avg_rec=$old_rec/$it
	let avg_tr=$old_tr/$it
	
	let velocity_recKB=$velocity_rec/1024
	let velocity_recMB=$velocity_recKB/1024
	let velocity_trKB=$velocity_tr/1024
	let velocity_trMB=$velocity_trKB/1024
	let avg_recKB=$avg_rec/1024
	let avg_recMB=$avg_recKB/1024
	let avg_trKB=$avg_tr/1024
	let avg_trMB=$avg_trKB/1024

	if [ $velocity_recMB != 0 ];
    then
 		velocity_rec="$velocity_recMB MB/s"
    else
		if [ $velocity_recKB != 0 ];
		then
			velocity_rec="$velocity_recKB KB/s"
		else
 			velocity_rec="$velocity_rec B/s"
		fi
    fi

	if [ $velocity_trMB != 0 ];
    then
 		velocity_tr="$velocity_trMB MB/s"
    else
		if [ $velocity_trKB != 0 ];
		then
			velocity_tr="$velocity_trKB KB/s"
		else
 			velocity_tr="$velocity_tr B/s"
		fi
    fi

	if [ $avg_recMB != 0 ];
    then
 		echoavg_rec="$avg_recMB MB/s"
    else
		if [ $avg_recKB != 0 ];
		then
			echoavg_rec="$avg_recKB KB/s"
		else
 			echoavg_rec="$avg_rec B/s"
		fi
    fi

	if [ $avg_trMB != 0 ];
    then
 		echoavg_tr="$avg_trMB MB/s"
    else
		if [ $avg_trKB != 0 ];
		then
			echoavg_tr="$avg_trKB KB/s"
		else
 			echoavg_tr="$avg_tr B/s"
		fi
    fi

	get_bat
	echo -n "$battery % "

	get_time
 	get_uptime $time

    echo -en "$interface DOWN:$velocity_rec  AVGDOWN:$echoavg_rec  UP:$velocity_tr  AVGUP:$echoavg_tr      \r"
   
    old_rec_bytes=$rec_bytes
    old_tr_bytes=$tr_bytes
 	let it=$it+1
   
    sleep 1;
 
done

