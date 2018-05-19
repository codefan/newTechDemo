#!/bin/bash

. "$( dirname "${BASH_SOURCE[0]}" )/setenv.sh"

if [ -f "$JSVC_PID_FILE" ]; then
    echo "Daemon is already running. ($( cat "$JSVC_PID_FILE" ))" >&2
    exit 1
fi

echo 'Starting Daemon in Foreground.'

echo $JSVC_EXECUTABLE  -java-home "$JAVA_HOME" -cp "$JAVA_CLASSPATH" -user "$JSVC_USER" \
    -nodetach -outfile ./log/outfile.log -errfile ./log/errfile.log  -pidfile $JSVC_PID_FILE \
    $JAVA_OPTS $JAVA_MAIN_CLASS

$JSVC_EXECUTABLE -server -cwd "$DIST_DIR" -java-home "$JAVA_HOME" -cp "$JAVA_CLASSPATH" -user "$JSVC_USER" \
    -nodetach -outfile ./log/outfile.log -errfile ./log/errfile.log -pidfile $JSVC_PID_FILE \
    $JAVA_OPTS $JAVA_MAIN_CLASS
