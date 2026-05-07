package planning

import kotlinx.serialization.Serializable

@Serializable
data class Plan(
    val title: String,
    val epics: List<Epic>,
    val totalPoints: Int,
    val estimatedSessions: String
)

@Serializable
data class Epic(
    val name: String,
    val description: String,
    val points: Int,
    val userStories: List<UserStory>
)

@Serializable
data class UserStory(
    val description: String,
    val tasks: List<Task>
)

@Serializable
data class Task(
    val description: String,
    val gradleTask: String
)
