# Paperwork
Generate build info for your Android project without breaking incremental compilation

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
Paperwork will generate this information (and more) for you and put it into a ```paperwork.json``` file inside your assets folder.
During runtime, you can access them lazy-loaded through getters like this:
```java
Paperwork paperwork = new Paperwork(context);
String gitSha = paperwork.getGitSha();
String buildTime = paperwork.getBuildTime();
String extra1 = paperwork.getExtra("myextra1");
String env1 = paperwork.getEnv("PWD");
``` 

Environment variables and injecting your own extras are supported. See the configuration below.  

Incremental builds are not broken, yay!

### Download and setup
Add these dependencies to your ```build.gradle```:

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath 'hu.supercluster:paperwork-plugin:1.1.0'
    }
}

apply plugin: 'hu.supercluster.paperwork'

// Completely optional configuration block, can be omitted:
paperwork {
    // You can generate the file somewhere else. Note however, that in order for it to be available
    // in Paperwork runtime, it has to be in the assets folder, and if the filename is not
    // paperwork.json, you have to inject its name either in the constructor, or through
    // Paperwork#setFilename().
    filename = 'src/main/assets/paperwork.json'
    
    // List any environment variables of interest to you. 
    // Access them later with Paperwork#getEnv(key).
    env = ['USER', 'PWD']
    
    // If for whatever reason you don't like the default format for git SHA and build time,
    // you can override them:
    gitSha = 'git rev-parse --short HEAD'.execute([], project.rootDir).text.trim()
    buildTime = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
     
    // You can inject your own extras like this, and access them later with Paperwork#getExtra(key).
    // Maybe use the output of a script here, I don't know. The only requirement is that it
    // should be a valid JSON object, or else horrible things will happen.
    extras = '{"mydata1": "foo bar", "mydata2": "lorem ipsum"}'
}
    
dependencies {
    compile 'hu.supercluster:paperwork:1.1.0'
}
```

Lastly, don't forget to add ```paperwork.json``` to your ```.gitignore``` file. 


### Contributing

Contributions are welcome! Got a question, found a bug, have a new feature idea? Submit an issue.

I'd love to hear about your usecase too, especially if it's not covered perfectly.


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
