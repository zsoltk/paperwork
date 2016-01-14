#!/usr/bin/env bash

./gradlew clean \
          build \
          :paperwork-plugin:test \
          :paperwork-runtime:test \
          :paperwork-integration:connectedCheck

