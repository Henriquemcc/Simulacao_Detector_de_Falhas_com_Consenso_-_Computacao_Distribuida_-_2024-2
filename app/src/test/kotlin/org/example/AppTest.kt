/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import io.github.henriquemcc.simulacao.detector.falhas.consenso.App
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull

class AppTest {
    @Test
    fun appHasAGreeting() {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
    }
}
