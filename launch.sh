#!/bin/bash

if [ ! -f target/uya-medius-tool-*-jar-with-dependencies.jar ]; then
	echo -e "$(tput bold)$(tput setaf 1)Error: run ./build.sh first!$(tput sgr0)"
	exit 1
fi

java -jar target/uya-medius-tool-*-jar-with-dependencies.jar $@
