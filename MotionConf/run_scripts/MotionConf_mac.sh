d=$(dirname $0)
java -Djava.library.path=${d}/lib/rxtx_natives/mac-10.5 -jar ${d}/MotionConf.jar
