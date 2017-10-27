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
    ArrayList<String> excludeList = []

    @Input
    ArrayList<String> cvExcludeList = []

    @Input
    int nbCvFiles = 5

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
        excludeList.each { basename ->
            basenameList.remove(basename)
        }
        def index = 0
        while ((cvList.getLength() < nbCvFiles) && (index < wavFiles.size()) ) {
            def file = wavFiles.getAt(index)
            def fileName = file.name - '.wav'
            if(!cvExcludeList.contains(file.name)) {
                basenameList.remove(fileName)
                cvList.add(fileName)
            }
            index++
        }
        // the list for training
        basenameList.write(bnFile)
        // the list for testing
        cvList.write(cvFile)
    }
}