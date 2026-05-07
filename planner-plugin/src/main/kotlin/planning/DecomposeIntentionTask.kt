package planning

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault(because = "LLM output is probabilistic — never cache")
abstract class DecomposeIntentionTask : DefaultTask() {

    @get:Input
    val intention: Property<String> = project.objects.property(String::class.java)

    @get:Optional
    @get:InputDirectory
    val specsDir: DirectoryProperty = project.objects.directoryProperty()

    @TaskAction
    fun decompose() {
        val intent = intention.get()
        val specContents = if (specsDir.isPresent) {
            SpecReader.read(specsDir.get().asFile.toPath())
        } else {
            emptyList()
        }
        val context = PlanningContext(intention = intent)
        val plan = IntentionPlanner.plan(intent, context, specContents, logger)
        val output = StdoutFormatter.format(plan)
        println(output)
    }
}
