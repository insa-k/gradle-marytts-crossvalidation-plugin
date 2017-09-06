package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import marytts.*


class SynthesizeCrossvalidationAudio extends DefaultTask {

    @InputDirectory
    File textDir = project.file("$project.buildDir/text")

    @InputDirectory
    File wavDir = project.file("$project.buildDir/wav")

    @InputDirectory
    File inputDir = project.file("$project.buildDir/crossvalidation/input")

    @OutputDirectory
    File cvTextDir = project.file("$project.buildDir/crossvalidation/output")

    @TaskAction
    void synthesize() {
        /* TODO use marytts-voicebuilding library for remote mary interface */
        fileTree(inputDir).include('*.xml').each { xmlFile ->
            def basename = xmlFile.name - '.xml'
            def doc = MaryDomUtils.parseDocument(xmlFile)
        }
        /*
        def mary = new marytts.client.RemoteMaryInterface()
        mary.inputType = 'RAWMARYXML'
        fileTree(generateCrossValidationInputFiles.destDir).include('*.xml').each { xmlFile ->
            def basename = xmlFile.name - '.xml'
            def doc = MaryDomUtils.parseDocument(xmlFile)
            mary.outputType = 'AUDIO'
            def audio = mary.generateAudio(doc)
            def samples = MaryAudioUtils.getSamplesAsDoubleArray(audio)
            def wavFile = file("$destDir/${basename}.wav")
            MaryAudioUtils.writeWavFile(samples, wavFile.path, audio.format)
            logger.lifecycle "Wrote $wavFile"
            def labFile = file("$destDir/${basename}.lab")
            mary.outputType = 'REALISED_DURATIONS'
            labFile.text = mary.generateText(doc)
            logger.lifecycle "Wrote $labFile"
        }
        */
    }
}