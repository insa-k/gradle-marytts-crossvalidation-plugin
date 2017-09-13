package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.util.io.BasenameList


class GenerateCrossvalidationInputFiles extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/text")

    @InputDirectory
    File labDir = project.file("$project.buildDir/lab")

    @InputDirectory
    File wavDir = project.file("$project.buildDir/wav")

    @OutputDirectory
    File refDir = project.file("$project.buildDir/crossvalidation/reference")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/crossvalidation/input")

    @InputFile
    File cvFile = project.file("$project.buildDir/crossvalidation/crossvalidation.lst")

    @TaskAction
    void getReference() {
        BasenameList cv = new BasenameList(cvFile.path)
        def String[] cvList = cv.getListAsArray()
        cvList.each { basename ->
            File wavFile = project.file("$wavDir/${basename + ".wav"}")
            File labFile = project.file("$labDir/${basename + ".lab"}")
            wavFile.renameTo(new File(refDir, wavFile.getName()));
            labFile.renameTo(new File(refDir, labFile.getName()));
        }
    }

    @TaskAction
    void generate() {
        def xmlString = this.class.getResourceAsStream('/de/dfki/mary/voicebuilding/templates/mary-template.xml').text
        ArrayList<String> cvListArray = new BasenameList(cvFile.path).getListAsArray()
        cvListArray.each { basename ->
            def txtString = project.file("$srcDir/${basename + '.txt'}").text
            def binding = ["text": txtString]
            def engine = new groovy.text.SimpleTemplateEngine()
            def template = engine.createTemplate(xmlString).make(binding)
            def xmlFile = project.file("$destDir/${ basename + '.xml'}") <<
                template.toString()
        }
        getReference()
    }
}
