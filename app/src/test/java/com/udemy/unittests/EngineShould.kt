package com.udemy.unittests

import com.udemy.tddmasterclass.Engine
import com.udemy.utils.MainCoroutineScopeRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class EngineShould {
    private val engine = Engine( )

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun turnedOn() = runTest {
        engine.turnOn()

        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun raiseTheTemperatureGraduallyWhenItTurnsOn() = runTest {
        val temperatureFlow = engine.turnOn()
        val actual = temperatureFlow.toList()

        assertEquals(listOf(25, 50, 95), actual)
    }
}