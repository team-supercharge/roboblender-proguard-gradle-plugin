#RoboBlender Proguard Gradle Plugin

With this plugin you can automatically generate the Proguard rules for the [RoboBlender](https://github.com/roboguice/roboguice/wiki/RoboBlender-wiki)
annotation database.


[![Build Status](https://travis-ci.org/team-supercharge/roboblender-proguard-gradle-plugin.svg?branch=development)](https://travis-ci.org/team-supercharge/roboblender-proguard-gradle-plugin)

## Usage

Add the plugin to `buildScript` `dependencies`:
```groovy
dependencies {
    classpath 'io.supercharge:roboblender-proguard-gradle-plugin:1.0-SNAPSHOT'
}
```

In your apps and library projects, apply the plugin:

```groovy
apply plugin: 'io.supercharge.roboblender-proguard-gradle-plugin'
```

## Contributing

Please fork this repository and create a pull request
Any contributions, large or small, major features, bug fixes, unit tests are welcomed and appreciated but will be thoroughly reviewed and discussed.

License
--------
This project is opensource, contribution and feedback are welcomed

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

    Copyright 2016 Supercharge

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## Author

[WonderCsabo](https://github.com/WonderCsabo)

[![Supercharge](http://s23.postimg.org/gbpv7dwjr/unnamed.png)](http://supercharge.io/)
