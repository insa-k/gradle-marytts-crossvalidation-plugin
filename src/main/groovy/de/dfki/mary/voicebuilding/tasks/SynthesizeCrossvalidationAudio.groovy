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

    @Input
    String locale = "en_US"

    @TaskAction
    void synthesize() {
        FileUtils.cleanDirectory(destDir)
        def batch = []
        def batchFile = project.file("$temporaryDir/batch.json")
        project.fileTree(srcDir).include("*.xml").each { xmlFile ->
            def wavFile = project.file("$destDir/${xmlFile.name - ".xml" + ".wav"}")
            if(batch.size() <= 300) {
                batch << [
                        locale    : locale,
                        inputType : "RAWMARYXML",
                        outputType: "AUDIO",
                        inputFile : "$xmlFile",
                        outputFile: "$wavFile"
                ]
            }
            else {
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
                batch.clear()
                batch << [
                        locale    : locale,
                        inputType : "RAWMARYXML",
                        outputType: "AUDIO",
                        inputFile : "$xmlFile",
                        outputFile: "$wavFile"
                ]
            }
        }
        if(batchFile.exists()) {
            batchFile.delete()
        }
        batchFile.createNewFile()
        if(batch.size() > 0 ) {
            batchFile.text = new JsonBuilder(batch).toPrettyString()

            project.javaexec {
                classpath project.configurations.marytts
                main 'marytts.BatchProcessor'
                args batchFile
                systemProperties << maryttsProperties
            }
        }

    }

}