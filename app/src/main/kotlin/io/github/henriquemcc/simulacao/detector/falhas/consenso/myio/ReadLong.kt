package io.github.henriquemcc.simulacao.detector.falhas.consenso.myio

import io.github.henriquemcc.simulacao.exclusao.mutua.distribuida.myio.readString
import java.text.MessageFormat

/**
 * Imprime mensagem de instrução e obtém do usuário um número inteiro (long) no intervalo especificado.
 * @param instructionMessage Mensagem que irá instruir o usuário o que digitar.
 * @param range Intervalo da entrada exigida.
 * @return Valor inteiro (long) digitado pelo usuário.
 */
fun readLong(instructionMessage: String? = null, range: LongRange? = null): Long {

    var number: Long? = null
    while (number == null || range?.contains(number) == false) {
        val stringRead = readString(instructionMessage)
        try {
            number = stringRead.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (range?.contains(number) == false) {
            println(MessageFormat.format("{0} is not in {1}", number, range))
        }
    }

    return number

}