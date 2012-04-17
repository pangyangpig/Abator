==========================================
    Create database ORM model for java                             
==========================================

#   usage
--------------

$ mvn clean compile jar:jar -X
$ cp target/domain-generator-0.0.1-SNAPSHOT.jar bin
$ cp config.properties bin
$ cd bin
$ java -classpath .:domain-generator-0.0.1-SNAPSHOT.jar:mysql-connector-java-5.1.18.jar com.xiebiao.tools.db.Creator 
$ cd output
