#!/bin/bash
sbt clean start-script && JAVA_OPTS="-javaagent:${HOME}/.ivy2/cache/org.aspectj/aspectjweaver/jars/aspectjweaver-1.7.4.jar" ./target/start

