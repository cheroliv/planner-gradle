package planning

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.ollama.OllamaChatModel
import java.time.Duration

object OllamaBridge {

    private const val DEFAULT_BASE_URL = "http://localhost:11434"
    private const val MODEL_NAME = "qwen3.5:397b-cloud"

    private val baseUrl: String
        get() = System.getenv("OLLAMA_BASE_URL") ?: DEFAULT_BASE_URL

    fun chatModel(): ChatModel = OllamaChatModel.builder()
        .baseUrl(baseUrl)
        .modelName(MODEL_NAME)
        .timeout(Duration.ofMinutes(5))
        .build()
}
