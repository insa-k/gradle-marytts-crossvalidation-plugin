package de.dfki.mary.voicebuilding

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

@Test
class MaryttsCrossvalidationPluginFunctionalTest {

//    GradleRunner gradle
//
//    @BeforeSuite
//    void setup() {
//        def projectDir = File.createTempDir()
//        gradle = GradleRunner.create().withProjectDir(projectDir).withPluginClasspath()
//        new File(projectDir, 'build.gradle').withWriter {
//            it << this.class.getResourceAsStream('build.gradle')
//        }
//    }
//
//    @Test
//    void canApplyPlugin() {
//        def result = gradle.withArguments().build()
//        assert true
//    }
//
//    @Test
//    void testPlugin() {
//        def result = gradle.withArguments('legacyInit').build()
//        println("Preparing Crossvalidation and running 'legacyInit'")
//        println result.output
//        println("\n_____________________________________\n" +
//                "Done")
//        def result2 = gradle.withArguments('build').build()
//        println("Building voice")
//        println result2.output
//        println("\n_____________________________________\n" +
//                "Done")
//        def result3 = gradle.withArguments('synthesizeCrossvalidationAudio').build()
//        println("synthesizeCrossvalidationAudio")
//        println result3.output
//        println("\n_____________________________________\n" +
//                "Done")
//        def result4 = gradle.withArguments('getRealisedDurations').build()
//        println("getRealisedDurations")
//        println result4.output
//        println("\n_____________________________________\n" +
//                "Done")
//        def result5 = gradle.withArguments('runCrossvalidation').build()
//        println("runCrossvalidation")
//        println result5.output
//        println("\n_____________________________________\n" +
//                "Done")
//    }
//
//    @Test
//    void testSelectCrossvalidationFiles() {
//        def result = gradle.withArguments('selectCrossvalidationFiles').build()
//        println result.output
//    }
//
//    @Test
//    void testGenerateCrossvalidationInputFiles() {
//        def result = gradle.withArguments('generateCrossvalidationInputFiles').build()
//        println result.output
//    }
//
//    @Test
//    void testSynthesizeCrossvalidationAudio() {
//        def result = gradle.withArguments('synthesizeCrossvalidationAudio').build()
//        println result.output
//    }
//    @Test
//    void testGetRealisedDurations() {
//        def result = gradle.withArguments('getRealisedDurations').build()
//        println result.output
//    }
//
//    @Test
//    void testRunCrossvalidation() {
//        def result = gradle.withArguments('runCrossvalidation').build()
//        println result.output
//    }

}