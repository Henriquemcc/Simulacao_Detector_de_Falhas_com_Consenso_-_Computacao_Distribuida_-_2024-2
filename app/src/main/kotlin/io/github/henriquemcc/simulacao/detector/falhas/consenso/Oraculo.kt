package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

/**
 * Representa um oráculo do detector de falhas.
 */
class Oraculo(
    private val detectorFalhasConsenso: DetectorFalhasConsenso,
    private val timeoutMilisegundos: Long,
): Thread() {

    /**
     * Armazena os estados dos processos.
     */
    private val timestampUltimoHeartbeat = mutableMapOf<Int, LocalDateTime>()

    /**
     * Obtém os estados dos processos.
     * @return Estado dos processos.
     */
    private fun obterEstadosProcessos(): Map<Int, EstadoProcesso> {
        val estadoProcessos = mutableMapOf<Int, EstadoProcesso>()
        timestampUltimoHeartbeat.forEach { (idProcesso, timestamp) ->
            estadoProcessos[idProcesso] = if (Duration.between(timestamp, LocalDateTime.now()).toMillis() < timeoutMilisegundos) {
                EstadoProcesso.CORRETO
            } else {
                EstadoProcesso.FALHO
            }
        }
        return estadoProcessos
    }

    override fun run() {
        while (!detectorFalhasConsenso.stopFlag.get()) {
            val estadosProcessos = obterEstadosProcessos()
            val processosFalhos = obterProcessosFalhos(estadosProcessos)
            println("Lista de processos falhos do Oráculo: $processosFalhos}")
            sleep(Random.nextLong(1000))
            val mensagensRecebidas = detectorFalhasConsenso.canalComunicacao.receberMensagemOraculo()
            for (mensagem in mensagensRecebidas) {
                when (mensagem) {
                    is Heartbeat -> timestampUltimoHeartbeat[mensagem.processoOrigem] = mensagem.relogio
                    is RequisicaoEstadoOutrosProcessos -> detectorFalhasConsenso.canalComunicacao.enviarMensagem(RespostaEstadoOutrosProcessos(estadosProcessos, mensagem.processoOrigem))
                }
            }
        }
    }
}