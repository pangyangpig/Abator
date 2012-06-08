==========================================
    Create database ORM model for java                             
==========================================

#   usage
--------------

$ mvn clean compile jar:jar -X
$ cp target/generator-0.0.1-SNAPSHOT.jar bin
$ cp config.properties bin
$ cd bin
$ java -classpath .:generator-0.0.1-SNAPSHOT.jar:mysql-connector-java-5.1.18.jar com.xiebiao.tools.Main 
$ cd output
