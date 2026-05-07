package planning

import org.gradle.api.Plugin
import org.gradle.api.Project

class PlanningPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val task = project.tasks.register(
            "decomposeIntention",
            DecomposeIntentionTask::class.java
        ) { task ->
            task.group = "planning"
            task.description = "Decomposes a natural language intention into a structured execution plan (EPICs → User Stories → Tasks)"
            task.intention.set(project.providers.gradleProperty("intention").orElse(""))
        }
    }
}
