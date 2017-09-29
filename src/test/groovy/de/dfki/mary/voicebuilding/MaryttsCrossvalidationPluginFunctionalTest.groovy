package de.dfki.mary.voicebuilding

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class MaryttsCrossvalidationPluginFunctionalTest {

    GradleRunner gradle

    @BeforeSuite
    void setup() {
        def projectDir = File.createTempDir()
        gradle = GradleRunner.create().withProjectDir(projectDir).withPluginClasspath()
        new File(projectDir, 'build.gradle').withWriter {
            it << this.class.getResourceAsStream('build.gradle')
        }
        new File(projectDir, 'settings.gradle').withWriter {
            it << this.class.getResourceAsStream('settings.gradle')
        }
    }

    @Test
    void canApplyPlugin() {
        def result = gradle.withArguments().build()
        assert true
    }

    @Test
    void testSelectCrossvalidationFiles() {
        def result = gradle.withArguments('selectCrossvalidationFiles').build()
        println result.output
    }

    @Test
    void testGenerateCrossvalidationInputFiles() {
        def result = gradle.withArguments('generateCrossvalidationInputFiles').build()
        println result.output
    }

    @Test
    void canBuildVoice() {
        def result = gradle.withArguments('legacyInit').build()
        println result.output
        def result2 = gradle.withArguments('build').build()
        println result2.output
    }

    @Test
    void testSynthesizeCrossvalidationAudio() {
        def result = gradle.withArguments('synthesizeCrossvalidationAudio').build()
        println result.output
    }

    @Test
    void testGetRealisedDurations() {
        def result = gradle.withArguments('getRealisedDurations').build()
        println result.output
    }

    @Test
    void testRunCrossvalidation() {
        def result = gradle.withArguments('runCrossvalidation').build()
        println result.output
    }

}