package planning

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault(because = "LLM output is probabilistic — never cache")
abstract class DecomposeIntentionTask : DefaultTask() {

    @get:Input
    val intention: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun decompose() {
        val plan = decomposeStub(intention.get())
        val output = StdoutFormatter.format(plan)
        println(output)
    }

    private fun decomposeStub(intention: String): Plan = Plan(
        title = intention,
        epics = listOf(
            Epic(
                name = "MVP-0",
                description = "Skeleton and bootstrap of the component",
                points = 5,
                userStories = listOf(
                    UserStory(
                        description = "Initialize project structure",
                        tasks = listOf(
                            Task("Create Gradle build files", "./gradlew scaffold"),
                            Task("Add .agents/ governance", "./gradlew initAgentGovernance")
                        )
                    ),
                    UserStory(
                        description = "Write initial tests",
                        tasks = listOf(
                            Task("Unit test skeleton", "./gradlew test"),
                            Task("Integration test setup", "./gradlew check")
                        )
                    )
                )
            ),
            Epic(
                name = "MVP-1",
                description = "Core business logic implementation",
                points = 8,
                userStories = listOf(
                    UserStory(
                        description = "Implement primary feature",
                        tasks = listOf(
                            Task("Main feature implementation", "./gradlew build"),
                            Task("Feature tests", "./gradlew test")
                        )
                    )
                )
            ),
            Epic(
                name = "MVP-2",
                description = "Dogfooding and validation",
                points = 3,
                userStories = listOf(
                    UserStory(
                        description = "Use the component on itself",
                        tasks = listOf(
                            Task("Self-test", "./gradlew dogfood"),
                            Task("Generate index", "./gradlew generateIndex")
                        )
                    )
                )
            )
        ),
        totalPoints = 16,
        estimatedSessions = "8-12"
    )
}
