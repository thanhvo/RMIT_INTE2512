#!/bin/bash
cd /Users/thanhvo/Workspace/RMIT/Marking/Assignment1_OOP/submissions
for filename in ./*; do
	echo "$filename"
	mkdir ${filename:0:15}
	unzip ${filename} -d ${filename:0:15}
done
