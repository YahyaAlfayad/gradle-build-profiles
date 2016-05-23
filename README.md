# Build Profiles Plugin

## Introduction

Build profiles plugin allows you to specify build profiles for your application in order to add/remove certain
sources and resources from the build according to the currently active profiles.

## Quick start

In order to get started with the build profiles plugin, you need to do the following:

* Add the build profiles plugin to your `build.gradle` as follows:

` apply plugin: 'gradle-buildprofiles-plugin'`

* Configure your build profiles in `build.gradle` as follows:

```

    buildProfilesConfig {
        buildProfiles = ['prod', 'uat']
    }

```

Note: you need to add the configuration block after the `apply plugin` block.

We have two build profiles in the previous example. They are `prod` and `uat`. 
you can add you own profiles.

An alternative way for specifying build profiles is to use `-DbuildProfiles=prod,uat` when calling
gradle from command line. This will override values specified in `build.gradle` and can be
useful when building using continuous integration system.

## Build profiles tasks

build profiles plugin has the following tasks:

* `createProfiles` : This task will create profile specific sources and resources directories.
For each specified programing language, there will be a separated set of profile specific directories.

* `createProfilesMainSources` : Like the previous task, but this one will create profile specific
**sources** directories for `src/main` folder.

* `createProfilesMainResources` : Like the previous but this one will create profile
specific **resources** directories.

* `createProfileTestSources` : Like the previous ones, but this task is responsible for
creating **sources** directories under `src/test` folder.

* `createProfileTestResources` : Like the previous one, but will create **resources**
directories under `src/test` folder.

## Further configurations

Build profiles plugin has the following configurations:

```

    buildProfilesConfig {
        programingLanguages = ['java', 'groovy', 'scala']
        buildProfiles = ['prod', 'uat']
        activeBuildProfiles = ['prod'] 
    }

```

By default programing languages will be a subset of java, groovy and scala that
are defined in your `build.gradle` by applying that language specific plugin. ie: if
you have `apply plugin: 'java'` in your `build.gradle` then java will be added to
programing languages.

You can override than behaviour and even define new programing languages by setting
the value of that property.

You can also define all of your profiles by setting `buildProfiles` property and
then set the active profiles according to you current environment by setting
`activeProfiles` property. By default the `activeBuildProfiles` property has the same
value as `buildProfiles` when it is not explicitly set.

You can also set `activeBuildProfiles` via `gradle.properties` file which exists
in your gradle home directory by specifying the following:

```
    systemProp.activeBuildProfiles=prod,uat
```

The previous way will override the value specified in `build.gradle`.

The same situation applies for `buildProfiles`. You can set the following in `gradle.properties`

```
    systemProp.buildProfiles=prod,uat
```

You can also use `-DactiveBuildProfiles=prod,uat` or `-DbuildProfiles=prod,uat`
when calling gradle from command line. This will override the values defined in `build.gradle`.

## Gradle plugin portal

This plugin is available in gradle plugin portal under the following link:

[https://plugins.gradle.org/plugin/net.yhf.build-profiles](https://plugins.gradle.org/plugin/net.yhf.build-profiles)
