#!/bin/bash
GROOVYMUD_HOME=/home/corbym/groovymud/

libdir="$GROOVYMUD_HOME/lib"
# Build the classpath
CP=$CP\:$GROOVYMUD_HOME/config

for jar in `ls $libdir`
do
   CP=$CP\:$libdir/$jar
done
echo CLASSPATH: $CP

MUDSPACE=$GROOVYMUD_HOME/groovy/mudspace
SCRIPTS=$GROOVYMUD_HOME/groovy/scripts
LIB=$libdir
CP=$CP\:$MUDSPACE

nohup "java" -server $CONF -classpath "$CP" -Dworkspace.loc="$GROOVYMUD_HOME" -Dgroovy-scripts="$SCRIPTS" -Dlib="$LIB" -Dmudspace="$MUDSPACE" -Djava.security.manager -Djava.security.policy=$GROOVYMUD_HOME/config/policy -Djava.security.auth.policy=$GROOVYMUD_HOME/config/policy.jaas -Djava.security.auth.login.config=$GROOVYMUD_HOME/config/jaas.config org.groovymud.engine.JMudEngine $@ >> ../logs/stderror.log &

echo GroovyMud started, use tail -f ../logs/groovymud.log to monitor logs
