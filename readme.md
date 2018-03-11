
# run h2 db locally
java -cp ~/.ivy2/cache/com.h2database/h2/jars/h2*.jar org.h2.tools.Server

# start jetty server from sbt-shell
~jetty:start

# stop jetty server from sbt-shell
~jetty:stop