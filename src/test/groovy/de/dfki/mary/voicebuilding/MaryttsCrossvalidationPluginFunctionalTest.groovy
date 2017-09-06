package de.dfki.mary.voicebuilding

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

@Test
class MaryttsCrossvalidationPluginFunctionalTest {

    GradleRunner gradle

    @BeforeSuite
    void setup() {
        def projectDir = File.createTempDir()
        gradle = GradleRunner.create().withProjectDir(projectDir).withPluginClasspath()
        new File(projectDir, 'build.gradle').withWriter {
            it << this.class.getResourceAsStream('build.gradle')
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
    void testSynthesizeCrossvalidationAudio() {
        def result = gradle.withArguments('synthesizeCrossvalidationAudio').build()
        println result.output
    }

}