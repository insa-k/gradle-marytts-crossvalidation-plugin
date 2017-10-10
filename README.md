# gradle-marytts-crossvalidation-plugin

[![Build Status](https://travis-ci.org/insa-k/gradle-marytts-crossvalidation-plugin.svg?branch=master)](https://travis-ci.org/insa-k/gradle-marytts-crossvalidation-plugin)

This plugin divides the voice-data in test and training sets. It then runs a crossvalidation which checks the overall performance of the voice.

## How to use this plugin

If you want to use this plugin in your voicebuilding project then.

Since this is still a SNAPSHOT-version this plugin is not yet on [plugins.gradle.org](plugins.gradle.org) so in order to use it you can publish it to your local maven repository via ` ./gradlew publish `.


Then add the following code snippet to the **build.gradle** of your voicebuilding-project.

```
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath "de.dfki.mary:gradle-marytts-crossvalidation-plugin:0.1-SNAPSHOT"
    }
}

apply plugin: 'de.dfki.mary.voicebuilding.marytts-crossvalidation'

```


Tasks can be used separately but if you want to make sure the task order is correct then add this to your build.gradle:

```
selectCrossvalidationFiles.dependsOn wav, text, lab

generateCrossvalidationInputFiles.dependsOn selectCrossvalidationFiles

legacyInit.dependsOn wav, text, lab, selectCrossvalidationFiles, generateCrossvalidationInputFiles

synthesizeCrossvalidationAudio.dependsOn('build')

getRealisedDurations.dependsOn synthesizeCrossvalidationAudio

runCrossvalidation.dependsOn getRealisedDurations, synthesizeCrossvalidationAudio, moveReferenceFiles
```

Make sure you run ` ./gradlew build ` after ` ./gradlew legacyInit` which initializes your voicebuilding process.

## Excluding files from basename.lst
If you have to exlude files from your voicebuilding process then you can add them to the ***exludeList** of `selectCrossvalidationFiles` like this:
```
selectCrossvalidationFiles.excludeList = ['utt0001']
```