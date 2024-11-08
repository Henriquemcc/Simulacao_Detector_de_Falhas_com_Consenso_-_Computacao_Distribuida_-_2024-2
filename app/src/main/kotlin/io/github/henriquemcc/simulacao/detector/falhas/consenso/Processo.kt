package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.util.concurrent.atomic.AtomicLong

class Processo(private val id: Int, private val detectorFalhasConsenso: DetectorFalhasConsenso): Thread() {
    override fun run() {
        println("Processo $id está em execução")
    }
}