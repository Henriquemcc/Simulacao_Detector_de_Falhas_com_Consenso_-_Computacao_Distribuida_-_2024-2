package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.util.concurrent.atomic.AtomicBoolean

class DetectorFalhasConsenso(
    private val numeroProcessos: Int,
    private val timeout: Long
): Thread() {

    /**
     * Flag que as threads dos processos vão olhar para finalizarem a execução.
     */
    var stopFlag = AtomicBoolean(false)

    /**
     * Lista de processos que serão executados.
     */
    private val processos = sortedSetOf<Processo>()

    /**
     * Canal de comunicação a ser utilizado pelos processos.
     */
    val canalComunicacao = CanalComunicacao(numeroProcessos)

    /**
     * Oráculo.
     */
    val oraculo = Oraculo(this, timeout)

    /**
     * Construtor da classe DetectorFalhasConsenso.
     */
    init {
        // Criando os processos
        for (i in 0..<numeroProcessos)
            processos.add(Processo(i, this))
    }

    /**
     * Executa os processos.
     */
    override fun run() {
        oraculo.start()
        for (processo in processos)
            processo.start()
    }
}