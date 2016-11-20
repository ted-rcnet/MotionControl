d=$(dirname $0)
java -Djava.library.path=${d}/lib/rxtx_natives/x86_64-unknown-linux-gnu -jar ${d}/MotionConf.jar
