package com.udemy.unittests

import com.udemy.tddmasterclass.Car
import com.udemy.tddmasterclass.Engine
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.udemy.utils.MainCoroutineScopeRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.mockito.kotlin.whenever

class CarShould {
    private val engine: Engine = mock()
    private val car: Car

    init {
        runTest {
            whenever(engine.turnOn()).thenReturn(flow {
                delay(2000)
                emit(25)
                delay(2000)
                emit(50)
                delay(2000)
                emit(95)
            })
        }
        car = Car(engine, 5.0)
    }

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun looseFuelWhenItTurnsOn() = runTest {
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnOnItsEngine() = runTest {
        car.turnOn()

        verify(engine, times(1)).turnOn()
    }
}