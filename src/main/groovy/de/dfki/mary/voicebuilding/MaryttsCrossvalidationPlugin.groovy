package de.dfki.mary.voicebuilding

import de.dfki.mary.voicebuilding.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class MaryttsCrossvalidationPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configurations {
            marytts
        }
        project.repositories {
            jcenter()
        }
        project.dependencies {
            marytts 'de.dfki.mary:marytts-voicebuilding:0.1'
            marytts 'de.dfki.mary:marytts-lang-en:5.2'
        }
        project.task('selectCrossvalidationFiles', type: SelectCrossvalidationFiles) {
            wavDir = project.selectCrossvalidationFiles.wavDir
            textDir = project.selectCrossvalidationFiles.textDir
            foldNb = project.selectCrossvalidationFiles.foldNb
        }
        project.task('generateCrossvalidationInputFiles', type: GenerateCrossvalidationInputFiles) {
            dependsOn SelectCrossvalidationFiles
            srcDir = project.selectCrossvalidationFiles.textDir
        }
    }
}