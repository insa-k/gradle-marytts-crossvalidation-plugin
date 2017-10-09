package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.apache.commons.io.FileUtils
import marytts.util.io.BasenameList


class MoveReferenceFiles extends DefaultTask {

    @InputDirectory
    File labDir = project.file("$project.buildDir/lab")

    @InputDirectory
    File wavDir = project.file("$project.buildDir/wav")

    @OutputDirectory
    File refDir = project.file("$project.buildDir/crossvalidation/reference")

    @InputFile
    File cvFile = project.file("$project.buildDir/crossvalidation/crossvalidation.lst")

    @TaskAction
    void getReference() {
        FileUtils.cleanDirectory(refDir)
        BasenameList cv = new BasenameList(cvFile.path)
        def String[] cvList = cv.getListAsArray()
        cvList.each { basename ->
            File wavFile = project.file("$wavDir/${basename + ".wav"}")
            File labFile = project.file("$labDir/${basename + ".lab"}")
            wavFile.renameTo(new File(refDir, wavFile.getName()));
            labFile.renameTo(new File(refDir, labFile.getName()));
        }
    }
}
