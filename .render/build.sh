#!/usr/bin/env bash

# Set JAVA_HOME manually (Render's default Java location)
export JAVA_HOME=/opt/render/project/.render/java

# Run Maven build using wrapper
./mvnw clean install
