package io.github.henriquemcc.simulacao.detector.falhas.consenso

/**
 * Obtém a lista dos processos falhos.
 * @param estadoProcessos Dicionário relacionando o id do processo com o seu estado.
 * @return Lista dos processos falhos.
 */
fun obterProcessosFalhos(estadoProcessos: Map<Int, EstadoProcesso>): Set<Int> {
    val processosFalhos = sortedSetOf<Int>()
    estadoProcessos.forEach { (idProcesso, estado) ->
        when (estado) {
            EstadoProcesso.FALHO -> processosFalhos.add(idProcesso)
            EstadoProcesso.CORRETO -> processosFalhos.remove(idProcesso)
        }
    }
    return processosFalhos
}