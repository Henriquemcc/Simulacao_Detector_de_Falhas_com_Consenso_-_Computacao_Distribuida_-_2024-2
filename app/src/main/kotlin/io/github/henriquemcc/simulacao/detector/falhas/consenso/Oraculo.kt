package io.github.henriquemcc.simulacao.detector.falhas.consenso

/**
 * Representa um oráculo do detector de falhas.
 */
class Oraculo: Thread() {

    /**
     * Armazena os estados dos processos.
     */
    private val estadosProcessos = mutableMapOf<Int, EstadoProcesso>()
}