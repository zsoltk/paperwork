# Paperwork
Generate git SHA and build time info for your Android project without breaking incremental compilation

### The problem
You want to include the git hash of the last commit and build time into your project, so that you can use their values in your crash reporting tool (for example).

The easiest way to do this is to generate them into your BuildConfig by adding these to your ```build.gradle```

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
Paperwork will generate this information for you and put it into a ```paperwork.json``` file inside your assets folder.
During runtime, you can access them lazy-loaded through getters like this:
```java
Paperwork paperwork = new Paperwork(context);
String gitSha = paperwork.getGitSha();
String buildTime = paperwork.getBuildTime();
JSONObject json = paperwork.getExtra();
``` 

The ```getExtra()``` is a handy method to access your own generated infos. See the configuration below.  

Incremental builds are not broken, yay!

### Download and setup
Add these dependencies to your ```build.gradle```:

```groovy
buildscript {
    repositories {
        mavenCentral()
        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    dependencies {
        classpath 'hu.supercluster:paperwork-plugin:1.0.0-SNAPSHOT'
    }
}

apply plugin: 'hu.supercluster.paperwork'

// Completely optional configuration block, can be omitted:
paperwork {
    // You can generate the file somewhere else. Note however, that in order for it to be available
    // in Paperwork runtime, it has to be in the assets folder, and if the filename is not
    // paperwork.json, you have to inject it's name either in the constructor, or through
    // Paperwork#setFilename()
    outputFilename = 'src/main/assets/paperwork.json'
     
    // You can inject your own extras like this. Maybe use the output of a script here, I don't know.
    // The only requirement is that it should be a valid JSON object, or else horrible things will happen.
    extra = '{"mydata1": "foo bar", "mydata2": "lorem ipsum"}'
}

repositories {
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}
    
dependencies {
    compile 'hu.supercluster:paperwork:1.0.0-SNAPSHOT'
}
```

Lastly, add ```paperwork.json``` to your ```.gitignore``` 


### Contributing

Contributions are welcome! Got a question, found a bug, have a new feature idea? Submit an issue with the appropriate label.


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
