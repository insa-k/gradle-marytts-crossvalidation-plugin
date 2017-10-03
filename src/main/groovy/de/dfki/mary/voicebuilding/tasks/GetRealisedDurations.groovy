package de.dfki.mary.voicebuilding.tasks

import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.*


class GetRealisedDurations extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/crossvalidation/input")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/crossvalidation/output")

    @Input
    Map<String, String> maryttsProperties = ['mary.base': "$project.buildDir/resources/legacy"]

    @TaskAction
    void getDuration() {
        def batch = []
        project.fileTree(srcDir).include("*.xml").each { xmlFile ->
            def labFile = project.file("$destDir/${xmlFile.name - ".xml" + ".lab"}")

            batch << [
                    locale    : "en_US",
                    inputType : "RAWMARYXML",
                    outputType: "REALISED_DURATIONS",
                    inputFile : "$xmlFile",
                    outputFile: "$labFile"
            ]
        }
        def batchFile = project.file("$temporaryDir/batch.json")

        batchFile.text = new JsonBuilder(batch).toPrettyString()

        project.javaexec {
            classpath project.configurations.marytts
            main 'marytts.BatchProcessor'
            args batchFile
            systemProperties << maryttsProperties
        }
    }

}