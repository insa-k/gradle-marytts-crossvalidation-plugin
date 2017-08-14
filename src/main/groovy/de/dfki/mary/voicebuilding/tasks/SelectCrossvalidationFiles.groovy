package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import java.io.File
import marytts.util.io.BasenameList

class SelectCrossvalidationFiles extends DefaultTask {

    @InputDirectory
    File textDir = project.file("$project.buildDir/text")

    @InputDirectory
    File wavDir = project.file("$project.buildDir/wav")

    @OutputDirectory
    File cvTextDir = project.file("$project.buildDir/crossvalidation/text")

    @OutputDirectory
    File cvWavDir = project.file("$project.buildDir/crossvalidation/wav")

    @Input
    int foldNb = 5

    @OutputDirectory
    File cvFile = project.file("$project.buildDir/crossvalidation/crossvalidation.lst")

    @OutputDirectory
    File bnFile = project.file("$project.buildDir/basenames.lst")

    @TaskAction
    void select() {
        ArrayList<File> wavFiles = wavDir.listFiles()
        ArrayList<File> shuffledFiles = Collections.shuffle(wavFiles)
        def cvList = new BasenameList()
        def basenameList = new BasenameList(wavDir.toPath().toString(), ".wav")
        0.upto(foldNb) { index ->
            def file = shuffledFiles.getAt(index)
            def fileName = file.name - '.wav'
            basenameList.remove(fileName)
            cvList.add(fileName)
        }
        // the list for training
        basenameList.write(bnFile)
        // the list for testing
        cvList.write(cvFile)
    }
}