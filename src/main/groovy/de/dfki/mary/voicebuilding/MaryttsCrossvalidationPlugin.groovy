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
            flatDir {
                dirs "$project.buildDir/libs"
            }
        }
        project.dependencies {
            marytts 'de.dfki.mary:marytts-voicebuilding:0.1'
            marytts 'de.dfki.mary:marytts-lang-en:5.2'
            marytts "${project.group}:${project.name}:${project.version}"
        }
        project.task('selectCrossvalidationFiles', type: SelectCrossvalidationFiles) {
        }
        project.task('generateCrossvalidationInputFiles', type: GenerateCrossvalidationInputFiles) {
            dependsOn project.selectCrossvalidationFiles
        }
        project.task('synthesizeCrossvalidationAudio', type: SynthesizeCrossvalidationAudio) {
            dependsOn project.generateCrossvalidationInputFiles
        }
        project.task('getRealisedDurations', type: GetRealisedDurations) {
            dependsOn project.synthesizeCrossvalidationAudio
        }
        project.task('runCrossvalidation', type: RunCrossvalidation) {
            dependsOn project.getRealisedDurations
        }
    }
}