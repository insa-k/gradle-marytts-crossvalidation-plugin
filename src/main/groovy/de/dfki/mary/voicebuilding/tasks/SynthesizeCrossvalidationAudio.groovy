package de.dfki.mary.voicebuilding.tasks

import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.*
import org.apache.commons.io.FileUtils

class SynthesizeCrossvalidationAudio extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/crossvalidation/input")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/crossvalidation/output")

    @Input
    Map<String, String> maryttsProperties = ['mary.base': "$project.buildDir/resources/legacy"]

    @TaskAction
    void synthesize() {
        FileUtils.cleanDirectory(destDir)
        def batch = []
        project.fileTree(srcDir).include("*.xml").each { xmlFile ->
            def wavFile = project.file("$destDir/${xmlFile.name - ".xml" + ".wav"}")

            batch << [
                    locale    : "en_US",
                    inputType : "RAWMARYXML",
                    outputType: "AUDIO",
                    inputFile : "$xmlFile",
                    outputFile: "$wavFile"
            ]
        }
        def batchFile = project.file("$temporaryDir/batch.json")
        if(batchFile.exists()) {
            batchFile.delete()
        }
        batchFile.createNewFile()

        batchFile.text = new JsonBuilder(batch).toPrettyString()

        project.javaexec {
            classpath project.configurations.marytts
            main 'marytts.BatchProcessor'
            args batchFile
            systemProperties << maryttsProperties
        }

    }

}