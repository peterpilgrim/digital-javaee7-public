#!/bin/bash

java_files=`find . -name "*.java" -o -name "build.gradle"`

for file in ${java_files}
do
	if  ! grep -iq Copyright ${file}
	then
		# echo "file=${file}"
		if [ -f ${file}.new ]; then
			/bin/rm -f ${file}.new
		fi
		cat COPYRIGHT.txt  $file > ${file}.new && mv -f ${file}.new ${file}
	fi
done
# End.
