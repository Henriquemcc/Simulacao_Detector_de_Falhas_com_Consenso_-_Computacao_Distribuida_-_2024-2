package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

/**
 * Classe que simula o funcionamento de um processo.
 * @param id ID do processo.
 * @param detectorFalhasConsenso Classe que instanciou esta classe.
 */
class Processo(private val id: Int, private val detectorFalhasConsenso: DetectorFalhasConsenso): Thread() {

    /**
     * Indica se o processo atual está falho.
     */
    private var falho = AtomicBoolean(false)

    /**
     * Conjunto com os ids dos processos falhos.
     */
    private val processosFalhos = sortedSetOf<Int>()

    /**
     * Executa a simulação do funcionamento de um processo.
     */
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