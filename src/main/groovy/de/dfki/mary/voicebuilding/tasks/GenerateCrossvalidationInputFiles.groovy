package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

class GenerateCrossvalidationInputFiles extends DefaultTask {

    @InputDirectory
    File srcDir = project.file("$project.buildDir/text")

    @OutputDirectory
    File destDir = project.file("$project.buildDir/crossvalidation/input")

    @TaskAction
    void generate() {
        fileTree(srcDir).include('*.txt').each { txtFile ->
            def basename = txtFile.name - '.txt'
            copy {
                from 'resources/de/dfki/mary/voicebuilding/templates'
                include 'blacklist.xml'
                expand([basename: basename, text: txtFile.text])
                rename { basename + '.xml' }
                into destDir
            }
        }
    }
}

