#!/bin/bash

JSVC_EXECUTABLE=jsvc
JSVC_PID_FILE=/tmp/commons-daemon-example.pid

if [ -z "$JSVC_USER" ]; then
    JSVC_USER="$USER"
fi

DIST_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"
LIB_DIR="$DIST_DIR/lib"
CONF_DIR="$DIST_DIR/conf"

JAVA_EXEC=java
JAVA_CLASSPATH="$LIB_DIR:$DIST_DIR/classes"
JAVA_MAIN_CLASS="com.centit.demo.daemon.DaemonApplication"
JAVA_OPTS="-Ddistribution.dir=$DIST_DIR"

if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME="$( $JAVA_EXEC -cp "$JAVA_CLASSPATH" -server)"
fi

export JSVC_EXECUTABLE JSVC_PID_FIL JSVC_USER DIST_DIR CONF_DIR JAVA_EXEC \
    JAVA_CLASSPATH JAVA_MAIN_CLASS JAVA_HOME
