package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.util.*

/**
 * Canal de comunicação entre os processos.
 */
class CanalComunicacao(private val numeroProcessos: Int) {

    /**
     * Mensagens a serem enviadas.
     */
    private val mensagens = Array(numeroProcessos + 1) {
        Collections.synchronizedList(mutableListOf<Mensagem>())
    }

    /**
     * Realiza o envio de mensagem.
     * @param mensagem Mensagem a ser enviada.
     */
    fun enviarMensagem(mensagem: Mensagem) {
        when (mensagem) {
            // Heartbeat
            is Heartbeat -> {
                mensagens[numeroProcessos].add(mensagem)
            }

            is RequisicaoEstadoOutrosProcessos -> {
                mensagens[numeroProcessos].add(mensagem)
            }

            is RespostaEstadoOutrosProcessos -> {
                mensagens[mensagem.processoDestino].add(mensagem)
            }
        }
    }

    /**
     * Realiza o recebimento das mensagens de um processo.
     * @return Lista de mensagens recebidas.
     */
    fun receberMensagemProcesso(idProcesso: Int): List<Mensagem> {
        val mensagensRecebidas = mutableListOf<Mensagem>()
        while (mensagens[idProcesso].isNotEmpty()){
            mensagensRecebidas.add(mensagens[idProcesso].removeFirst())
        }
        return mensagensRecebidas
    }

    /**
     * Realiza o recebimento das mensagens do oráculo.
     * @return Lista de mensagens recebidas.
     */
    fun receberMensagemOraculo(): List<Mensagem> {
        return receberMensagemProcesso(numeroProcessos)
    }

}
