package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

/**
 * Classe que simula o funcionamento de um processo.
 * @param id ID do processo.
 * @param detectorFalhasConsenso Classe que instanciou esta classe.
 */
class Processo(private val id: Int, private val detectorFalhasConsenso: DetectorFalhasConsenso): Thread(), Comparable<Processo> {

    /**
     * Indica se o processo atual está falho.
     */
    private var falho = AtomicBoolean(false)

    /**
     * Executa a simulação do funcionamento de um processo.
     */
    override fun run() {
        println("Processo $id está em execução")
        daemonMensagens.start()
        while (!detectorFalhasConsenso.stopFlag.get()) {
            sleep(Random.nextLong(1000))

            // Falhando
            falho.set(Random.nextBoolean())
            while (falho.get() && !detectorFalhasConsenso.stopFlag.get()) {
                println("O processo $id está falho")
                sleep(2000)

                // Saindo da falha
                falho.set(Random.nextBoolean())

                if (!falho.get())
                    println("O processo $id se recuperou da falha")
            }
        }
    }

    /**
     * Thread responsável pelo envio e recebimento de mensagens.
     */
    private val daemonMensagens = Thread {
        while (!detectorFalhasConsenso.stopFlag.get()) {
            sleep(Random.nextLong(1000))

            // Enviando heartbeats
            if (!falho.get()) {
                println("O processo $id enviou seu heartbeat")
                detectorFalhasConsenso.canalComunicacao.enviarMensagem(Heartbeat(id, LocalDateTime.now()))


                // Recebendo mensagens
                var processosFalhos = setOf<Int>()
                val mensagensRecebidas = detectorFalhasConsenso.canalComunicacao.receberMensagemProcesso(id)
                for (mensagem in mensagensRecebidas) {
                    if (mensagem is RespostaEstadoOutrosProcessos) {
                        processosFalhos = obterProcessosFalhos(mensagem.estadoProcessos)
                    }
                }
                println("Lista de processos falhos de $id: $processosFalhos")

                // Solicitando os estados dos processos
                println("O processo $id solicitou o estado dos outros processos")
                detectorFalhasConsenso.canalComunicacao.enviarMensagem(RequisicaoEstadoOutrosProcessos(id))
            }
        }
    }

    /**
     * Compara um Processo com outro.
     * @param other Outro processo a ser comparado com este.
     * @return Retorna 0 se esta instância for igual, um número negativo se esta instância for menor e um número positivo se esta instância for maior que a outra.
     */
    override fun compareTo(other: Processo): Int {
        return this.id.compareTo(other.id)
    }
}