package io.github.henriquemcc.simulacao.detector.falhas.consenso

class RespostaEstadoOutrosProcessos(
    val estadoProcessos: Map<Int, EstadoProcesso>,
    val processoDestino: Int
): Mensagem