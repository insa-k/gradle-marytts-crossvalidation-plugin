package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.util.io.BasenameList

class SelectCrossvalidationFiles extends DefaultTask {

    @InputDirectory
    File textDir = project.file("$project.buildDir/text")

    @InputDirectory
    File wavDir = project.file("$project.buildDir/wav")

    @Input
    int foldNb = 5

    @OutputFile
    File cvFile = project.file("$project.buildDir/crossvalidation/crossvalidation.lst")

    @OutputFile
    File bnFile = project.file("$project.buildDir/basenames.lst")

    @TaskAction
    void select() {
        ArrayList<File> wavFiles = wavDir.listFiles()
        Collections.shuffle(wavFiles)
        def cvList = new BasenameList()
        def basenameList = new BasenameList(wavDir.toPath().toString(), ".wav")
        0.upto(foldNb -1 ) { index ->
            def file = wavFiles.getAt(index)
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