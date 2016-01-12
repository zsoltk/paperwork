#!/usr/bin/env bash

cp paperwork-plugin/src/main/groovy/* buildSrc/src/main/groovy/ -r

./gradlew clean \
          build \
          :paperwork-plugin:test \
          :paperwork-runtime:test \
          :paperwork-integration:connectedCheck

