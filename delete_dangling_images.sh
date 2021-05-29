#!/bin/bash

# Remove any dangling dockers in the system
docker rmi $(docker images -f "dangling=true" -q)
