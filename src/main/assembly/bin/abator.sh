#!/bin/sh

export CLASSPATH=$CLASSPATH:../libs/*:../conf/log4j.properties
echo $CLASSPATH
java -classpath $CLASSPATH com.github.abator.Main 
