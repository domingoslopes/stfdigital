#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/discovery/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/processos/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/userauthentication/info" 150