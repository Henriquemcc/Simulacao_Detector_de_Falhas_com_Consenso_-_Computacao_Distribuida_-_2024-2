package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class Processo(private val id: Int, private val detectorFalhasConsenso: DetectorFalhasConsenso): Thread() {

    private var falho = AtomicBoolean(false)

    override fun run() {
        println("Processo $id está em execução")
        while (!detectorFalhasConsenso.stopFlag.get()) {
            sleep(Random.nextLong(15000))

            // Falhando
            falho.set(Random.nextBoolean())
            while (falho.get()) {
                println("O processo $id está falho")
                sleep(Random.nextLong(60000))

                // Saindo da falha
                falho.set(Random.nextBoolean())

                if (!falho.get())
                    println("O processo $id se recuperou da falha")
            }
        }
    }
}