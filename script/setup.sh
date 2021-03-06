echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo CYGWIN setup script
echo To run this you should:
echo + have JAVA_HOME defined
echo + have ANT installed
echo + either
echo + - run all commands, including this one, from the directory containing the build.xml file, OR
echo + - define MINORTHIRD to be WINDOWS name of that directory, eg c:\minorthird
echo + rather than /cygdrive/c/minorthird
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo
export CLASSPATH="${CLASSPATH};.;${MINORTHIRD-.}/class;${MINORTHIRD-.}/lib;${MINORTHIRD-.}/lib/minorThirdIncludes.jar"
export CLASSPATH="${CLASSPATH};.;${MINORTHIRD-.}/lib/mixup;${MINORTHIRD-.}/config"
export MONTYLINGUA="${MINORTHIRD-.}/lib/montylingua"
export ORG="${MINORTHIRD-.}/lib/poi/src/java/org"

