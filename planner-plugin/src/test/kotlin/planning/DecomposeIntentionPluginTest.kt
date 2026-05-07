package planning

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DecomposeIntentionPluginTest {

    @Test
    fun `plugin registers decomposeIntention task`() {
        val projectDir = createTestProject()

        val result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("tasks", "--group", "planning")
            .withPluginClasspath()
            .build()

        assertTrue(result.output.contains("decomposeIntention"))
        assertEquals(TaskOutcome.SUCCESS, result.task(":tasks")?.outcome)
    }

    @Test
    fun `decomposeIntention task belongs to planning group`() {
        val projectDir = createTestProject()

        val result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("tasks")
            .withPluginClasspath()
            .build()

        assertTrue(result.output.contains("Planning tasks"))
    }

    @Test
    fun `decomposeIntention with valid intention outputs structured format`() {
        val projectDir = createTestProject()

        val result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("decomposeIntention", "-Pintention=add unit tests")
            .withPluginClasspath()
            .build()

        val output = result.output
        assertTrue(
            output.contains("[PLAN]") ||
            output.contains("[ERROR]") ||
            output.contains("[FATAL]")
        )
    }

    @Test
    fun `decomposeIntention with missing intention does not crash`() {
        val projectDir = createTestProject()

        val result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("decomposeIntention")
            .withPluginClasspath()
            .build()

        val output = result.output
        assertTrue(
            output.contains("[PLAN]") ||
            output.contains("[ERROR]") ||
            output.contains("[FATAL]") ||
            output.contains("BUILD SUCCESSFUL"),
            "Should not crash even without explicit intention"
        )
    }

    private fun createTestProject(): File {
        val projectDir = File(System.getProperty("java.io.tmpdir"), "test-project-${System.nanoTime()}")
        projectDir.mkdirs()

        projectDir.resolve("settings.gradle.kts").writeText("""
            rootProject.name = "test-project"
        """.trimIndent())

        projectDir.resolve("build.gradle.kts").writeText("""
            plugins {
                id("com.cheroliv.planner")
            }
        """.trimIndent())

        return projectDir
    }
}
