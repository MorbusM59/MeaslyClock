package com.measlyclock

import com.measlyclock.data.*
import org.junit.Assert.*
import org.junit.Test

class AlarmSetCycleTest {

    @Test
    fun `standalone set toggles on and off`() {
        val set = AlarmSet(
            id = "s1", name = "Test", color = androidx.compose.ui.graphics.Color.Red,
            type = SetType.STANDALONE, isActive = false
        )
        assertFalse(set.isActive)
        val toggled = set.copy(isActive = !set.isActive)
        assertTrue(toggled.isActive)
    }

    @Test
    fun `grouped cycle advances through members then null`() {
        val members = listOf("a", "b", "c")
        val group = CycleGroup(id = "g1", name = "G", memberSetIds = members, activeSetId = null)

        fun nextActive(current: String?): String? {
            val idx = members.indexOf(current)
            return when {
                idx == -1 -> members.first()
                idx < members.size - 1 -> members[idx + 1]
                else -> null
            }
        }

        assertNull(group.activeSetId)
        assertEquals("a", nextActive(group.activeSetId))
        assertEquals("b", nextActive("a"))
        assertEquals("c", nextActive("b"))
        assertNull(nextActive("c"))
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
