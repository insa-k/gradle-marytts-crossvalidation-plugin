
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
[![Build Status](https://travis-ci.org/insa-k/gradle-marytts-crossvalidation-plugin.svg?branch=master)](https://travis-ci.org/insa-k/gradle-marytts-crossvalidation-plugin)

# gradle-marytts-crossvalidation-plugin

This plugin divides the voice-data in a test and training set and then runs a cross-validation.
The results can give an insight on the overall performance of the voice. 

## How to apply this plugin

See https://plugins.gradle.org/plugin/de.dfki.mary.voicebuilding.marytts-crossvalidation

## Setup

All of these tasks can be used separately (provided the required data is given) but if you want to make sure the task order is correct then add this to your `build.gradle`:

```
selectCrossvalidationFiles.dependsOn wav, text, lab

generateCrossvalidationInputFiles.dependsOn selectCrossvalidationFiles

legacyInit.dependsOn wav, text, lab, selectCrossvalidationFiles, generateCrossvalidationInputFiles, moveReferenceFiles

getRealisedDurations.dependsOn synthesizeCrossvalidationAudio

runCrossvalidation.dependsOn getRealisedDurations, synthesizeCrossvalidationAudio
```

Make sure you run ` ./gradlew build ` after ` ./gradlew legacyInit` which initializes your voicebuilding process.

### Specifiying the length of `crossvalidation.lst`
If you want to specify the length of the `crossvalidation.lst` (for example when you use a *k-folding* method) then you can specify it like this:
```
selectCrossvalidationFiles.nbCvFiles = 10
```
The default number of files that are used in one crossvalidation is **5**.

### Excluding files from `basename.lst`
If you have to exclude files from your voicebuilding process then you can add them to the `exludeList` of `selectCrossvalidationFiles` like this:
```
selectCrossvalidationFiles.excludeList = ['utt0001']
```

### Excluding files from `crossvalidation.lst`
You can also exclude files from your crossvalidation list. This is often the case when you want to do several iterations and do not want to have an utterance twice in the test set. You can add these files this:
```
selectCrossvalidationFiles.cvExcludeList = ['utt0001']
```

### Example project
[Here](https://github.com/insa-k/voice-cmu-rms) you can see an example project using this plugin for a 10-fold cross-validation.
