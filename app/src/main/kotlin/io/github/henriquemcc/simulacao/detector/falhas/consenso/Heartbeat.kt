package io.github.henriquemcc.simulacao.detector.falhas.consenso

import java.time.LocalDateTime

class Heartbeat(
    val processoOrigem: Int,
    val relogio: LocalDateTime
): Mensagem