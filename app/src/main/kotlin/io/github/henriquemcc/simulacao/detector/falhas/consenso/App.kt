package io.github.henriquemcc.simulacao.detector.falhas.consenso

import io.github.henriquemcc.simulacao.detector.falhas.consenso.myio.readLong
import io.github.henriquemcc.simulacao.exclusao.mutua.distribuida.myio.printHeader
import io.github.henriquemcc.simulacao.exclusao.mutua.distribuida.myio.readDouble
import io.github.henriquemcc.simulacao.exclusao.mutua.distribuida.myio.readInteger
import kotlin.math.floor

fun main() {

    // Imprimindo cabeçalho
    printHeader("Detector de Falhas com Consenso")

    // Obtendo quantidade de processos
    val numeroProcessos = readInteger("Número de processos: ", IntRange(1, Int.MAX_VALUE))

    // Obtendo o tempo de simulação
    val tempoSimulacao = readDouble("Tempo de simulação (em segundos): ", LongRange(1, Double.MAX_VALUE.toLong()))

    // Obtendo o timeout
    val timeout = readLong("Timeout (em milissegundos): ", LongRange(1, Long.MAX_VALUE))

    // Criando uma instância do detector de falhas
    val detectorFalhasConsenso = DetectorFalhasConsenso(numeroProcessos, timeout)

    // Iniciando a simulação
    detectorFalhasConsenso.start()

    // Finalizando o programa após ele ter sido executado pelo tempo especificado
    Thread.sleep(floor(tempoSimulacao * 1000).toLong())
    detectorFalhasConsenso.stopFlag.set(true)

}
