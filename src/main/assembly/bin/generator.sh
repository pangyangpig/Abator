#!/bin/sh

mvn clean compile jar:jar dependency:copy-dependencies
java -classpath .:./target/dependency/*:./target/generator-0.0.1-SNAPSHOT.jar \
com.xiebiao.tools.Main 
