#!/bin/bash
export JAVA_HOME=/home/marco/jdk21/jdk-21.0.10
export PATH="$JAVA_HOME/bin:$PATH"
java --version
cd "/home/marco/minecraft mods/lucas mods"
./gradlew build 2>&1
