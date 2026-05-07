package planning

object StdoutFormatter {

    fun format(plan: Plan): String = buildString {
        appendLine("[PLAN] title=\"${plan.title}\" totalPoints=${plan.totalPoints} estimatedSessions=${plan.estimatedSessions}")
        for (epic in plan.epics) {
            appendLine("[EPIC] name=\"${epic.name}\" description=\"${epic.description}\" points=${epic.points}")
            for (us in epic.userStories) {
                appendLine("  [US] description=\"${us.description}\"")
                for (task in us.tasks) {
                    appendLine("    [TASK] description=\"${task.description}\" gradleTask=${task.gradleTask}")
                }
            }
        }
    }
}
