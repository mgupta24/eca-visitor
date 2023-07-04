FROM adoptopenjdk/openjdk11:jre-11.0.19_7-ubuntu
COPY target/eca-visitor-*.jar /ecavisitor.jar
ENV JAVA_OPTS=""
ENV APP_NAME="ecaapartmentcatalog"
ENV SECURITY_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENV MEMORY_OPTS=" -XX:+UseG1GC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:MaxMetaspaceSize=256m \
-XX:HeapDumpPath=/tmp/Heapdump.hprof \
-XX:+UseStringDeduplication"
CMD java $SECURITY_OPTS $MEMORY_OPTS $JAVA_OPTS -jar /ecavisitor.jar