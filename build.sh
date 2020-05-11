#!/bin/bash

# Remove old build and rebuild
rm target/*.jar
mvn package
mvn assembly:assembly
