#!/bin/bash

cygwin=false
if [ `uname | grep CYGWIN` ]; then
  cygwin=true
fi

if [ "$JAVA_HOME" = "" ]; then
  echo "JAVA_HOME is not defined"
  exit 1
fi

jetty_instance=$1

if [ "$jetty_instance" = "" ]; then
  echo "Need to specify which instance of Jetty: 9081 or 9082"
  exit 1
fi

unset CDPATH
root=`dirname $0`/..
root=`cd $root && pwd`

tc_install_dir=$root/bin/`$root/bin/relative-paths.sh tc_install_dir`

jetty_work_dir=$root/jetty6.1/$jetty_instance
jetty_home=$tc_install_dir/third-party/jetty-6.1.15
start_jar=$jetty_home/start.jar
stop_port=$((jetty_instance + 2))

cd $jetty_work_dir
mkdir -p logs
if $cygwin; then
  tc_install_dir=`cygpath -w $tc_install_dir`
  jetty_work_dir=`cygpath -w $jetty_work_dir`
  jetty_home=`cygpath -w $jetty_home`
  start_jar=`cygpath -w $start_jar`
fi

echo "starting Jetty $jetty_instance..."
$JAVA_HOME/bin/java -Xmx256m -XX:MaxPermSize=128m -Dtc.install-root=$tc_install_dir -Djetty.home=$jetty_home \
 -Djava.security.egd=file:/dev/./urandom \
 -DSTOP.PORT=$stop_port \
 -DSTOP.KEY=secret -jar $start_jar conf.xml &
sleep 1
echo
