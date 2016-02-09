# Paperwork
[![Build Status](https://travis-ci.org/zsoltk/paperwork.svg?branch=master)](https://travis-ci.org/zsoltk/paperwork)

Generate build info for your Android project without breaking incremental compilation

### The problem
A common use case is that you want to include the git hash of the last commit and build time into your project, so that you can use their values in your crash reporting tool (for example).

The easiest way to do this is to generate them into your ```BuildConfig``` by adding these to your ```build.gradle```

```groovy
def gitSha = 'git rev-parse --short HEAD'.execute([], project.rootDir).text.trim()
def buildTime = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))

android {
    defaultConfig {
        buildConfigField "String", "GIT_SHA", "\"${gitSha}\""
        buildConfigField "String", "BUILD_TIME", "\"${buildTime}\""
    }
}
```

But this will break incremental builds, resulting in increased build times all the time.


### What this lib offers
Paperwork can generate this information (and more) for you, and put it into a ```paperwork.json``` file inside your assets folder instead of using ```BuildConfig```, and helps you read it from there:

```java
Paperwork paperwork = new Paperwork(context);
String gitSha = paperwork.get("gitSha");
String buildTime = paperwork.get("buildTime");
```

Not just git hash, not just build time: you define what gets generated, and you can use anything that otherwise would break incremental builds.

Many helpers are available for the most common scenarios. See the configuration below.

### Build time comparison
Measured three consecutive builds per type, running gradle daemon, hitting "Run 'app'" in Android Studio without touching anything else. Generated info: git hash and build time (using seconds) so that it has a new value every time.

* Without generating build info: *3.989s*, *3.915s*, *3.902s*
* Using BuildConfig fields: *14.843s*, *13.844s*, *13.194s*
* Using Paperwork: *4.356s*, *4.075s*, *4.042s*

### Download and setup
Add these dependencies to your ```build.gradle```:

```groovy
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath 'hu.supercluster:paperwork-plugin:1.2.4'
    }
}

apply plugin: 'hu.supercluster.paperwork'

paperwork {
    // Configuration comes here, see next section for details
}

dependencies {
    compile 'hu.supercluster:paperwork:1.2.4'
}
```

Lastly, don't forget to add ```paperwork.json``` to your ```.gitignore``` file.

### Configuration
Paperwork doesn't generate anything by default, you have to define whatever data you need in simple key-value pairings. For a list of helper methods you can use, see next section.

```groovy
paperwork {
    set = [
        someKey1: "someValue",
        someKey2: someHelperMethod()
    ]
}
```

All the data will be available at runtime by querying for your own defined keys:

```java
Paperwork paperwork = new Paperwork(context);
String data1 = paperwork.get("someKey1");        // will return "someValue"
String data1 = paperwork.get("someKey2");        // will return the result of someHelperMethod()
```

You can also change the default filename or generate the file somewhere else:

```groovy
paperwork {
    filename = 'src/main/assets/paperwork.json'
}
```

Note however, that in order for it to be available in Paperwork runtime,
it has to be in the assets folder, and if the filename is not
paperwork.json, you have to inject its name in the constructor:

```java
Paperwork paperwork = new Paperwork(context, "paperwork.json");
```


### Helpers

```groovy
buildTime()
```
Simple unix timestamp (ms)


```groovy
buildTime("yyyy-MM-dd HH:mm:ss")
```
Formatted date string


```groovy
buildTime("yyyy-MM-dd HH:mm:ss", "GMT")
```
Formatted date string for a given timezone


```groovy
gitSha()
```
The current git SHA


```groovy
gitTag()
```
The last git tag (lightweight tags included)


```groovy
gitInfo()
```
Runs ```git describe --tags --always --dirty```. Returns a result like "v2.1.0-71-gb88c59a-dirty"
(The last tag + how many commits ahead of that tag are we now in the working tree + current hash + whether the working tree has uncommited changes)


```groovy
shell("scripts/test.sh")
```
Runs a shell command and returns its output (doesn't have to be a script)


```groovy
env("SOME_ENV")
```
Returns the value of an environment variable


### Contributing

Contributions are welcome! Got a question, found a bug, have a new helper method idea? Submit an issue and discuss it!

I'd love to hear about your use case too, especially if it's not covered perfectly.


### License

    Copyright 2015 Zsolt Kocsi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
