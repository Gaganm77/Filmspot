#!/bin/bash

# Set Java environment manually
export JAVA_HOME=/opt/render/project/.render/java

# Optional: echo JAVA version
echo "Using JAVA_HOME=$JAVA_HOME"
java -version

# Run Maven build
./mvnw clean install
