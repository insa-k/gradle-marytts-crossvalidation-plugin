package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.util.io.BasenameList


class GenerateCrossvalidationInputFiles extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/text")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/crossvalidation/input")

    @InputFile
    File cvFile = project.file("$project.buildDir/crossvalidation/crossvalidation.lst")

    @TaskAction
    void generate() {
        def xmlString = this.class.getResourceAsStream('mary-template.xml').toString()
        ArrayList<String> cvListArray = new BasenameList(cvFile.path).getListAsArray()
        cvListArray.each { basename ->
            def txtFile = project.file("$srcDir/${basename + '.txt'}")
            def xmlFile = project.file("$destDir/${ basename + '.xml'}") <<
                txtFile.text + "\n" + xmlString
        }
    }
}

