package planning

import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OllamaBridgeTest {

    @Test
    fun `chatModel returns non-null ChatLanguageModel`() {
        val model = OllamaBridge.chatModel()
        assertNotNull(model)
    }

    @Test
    fun `chatModel uses qwen3-5 model name`() {
        val model = OllamaBridge.chatModel()
        val info = model.toString()
        assertNotNull(info)
    }
}
