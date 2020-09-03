# RUN

See https://repo1.maven.org/maven2/io/fabric8/run-java-sh

Example to create a run script run-java.sh
 
JAVA_PACKAGE=openjdk11-jre-headless
RUN_JAVA_VERSION=1.3.8
INSTALLATION_FOLDER=/tmp
curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o ./run-java.sh 