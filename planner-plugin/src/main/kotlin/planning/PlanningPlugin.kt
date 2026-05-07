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
            task.description = "Decomposes a natural language intention into a structured execution plan (EPICs → User Stories → Tasks). Optional: -PspecsDir=/path/to/specs for RAG context."
            task.intention.set(project.providers.gradleProperty("intention").orElse(""))
            val specsDirProp = project.providers.gradleProperty("specsDir")
            if (specsDirProp.isPresent) {
                task.specsDir.set(project.layout.projectDirectory.dir(specsDirProp.get()))
            }
        }
    }
}
