!/bin/bash

gfsh -e "run --file=/Users/jblum/pivdev/spring-data-examples-workspace/spring-gemfire-clusterconfiguration-workspace/gemfire-bootstrap/bin/startLocatorServer.gfsh"
gfsh -e "run --file=/Users/jblum/pivdev/spring-data-examples-workspace/spring-gemfire-clusterconfiguration-workspace/gemfire-bootstrap/bin/stopLocatorServer.gfsh"
