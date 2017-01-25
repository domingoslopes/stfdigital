#!/usr/bin/env bash

set -e

push_images () {

	if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
	
		if [[ "$2" == "master" ]]; then
			docker tag $IMAGE_NAME "$1:latest"
			docker push "$1:latest"
			docker tag $IMAGE_NAME "$1:$VERSION"
			docker push "$1:$VERSION"
		fi
		
		if [[ "$2" == "dvelop" ]]; then
			docker tag $IMAGE_NAME "$1:$VERSION-develop"
			docker push "$1:$VERSION-develop"
		fi
		
		if [[ $2 =~ ^[R|r][L|l]-[0-9]+\.[0-9]+\.[0-9]+-[R|r][C|c]([0-9]*)$ ]]; then
			docker tag $IMAGE_NAME "$1:$VERSION-RC${BASH_REMATCH[1]}"
			docker push "$1:$VERSION-RC${BASH_REMATCH[1]}"				
		fi
	fi
}

BRANCH=$(if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then echo $TRAVIS_BRANCH; else echo $TRAVIS_PULL_REQUEST_BRANCH; fi)
echo "TRAVIS_BRANCH=$TRAVIS_BRANCH, PR=$PR, BRANCH=$BRANCH"

docker login -u $DOCKER_USER_DH -p $DOCKER_PASS_DH
push_images "supremotribunalfederal/$IMAGE_NAME" $BRANCH 

docker login -u $DOCKER_USER -p $DOCKER_PASS registry.stf.jus.br
push_images "registry.stf.jus.br/stfdigital/$IMAGE_INTERNAL_REGISTRY_NAME" $BRANCH 