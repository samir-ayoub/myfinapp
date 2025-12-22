package com.knowledge.myfinapp.data.notification.delegate

class MerchantNormalizerImpl: MerchantNormalizer {
    override fun normalize(text: String): String? {
        var cleanedText = text
            .trim()
            .lowercase()
            .replace("\n", " ")
            .removeAmounts()
            .removeNoise()

        // removing known prefixes
        PREFIXES.forEach { prefix ->
            if (cleanedText.startsWith(prefix)) {
                cleanedText = cleanedText.removePrefix(prefix)
            }
        }

        val tokens = cleanedText.split(" ")
            .filter { it.isNotBlank() }
            .filterNot { it.lowercase() in STOP_WORDS_LOWER }

        if (tokens.isEmpty()) return null

        return tokens
            .joinToString(" ")
            .uppercase()
            .take(MAX_CHARS)
    }

    private fun String.removeAmounts(): String =
        this.replace(Regex("""(r\$|€|eur)\s?[\d\.]+,\d{2}"""), "")

    private fun String.removeNoise(): String =
        this.replace(Regex("""[*#\d]"""), " ")

    companion object {
        var MAX_CHARS = 40
        var STOP_WORDS = listOf(
            "compra", "aprovada", "aprovado", "valor", "no", "em",
            "cartao", "credito", "debito", "transacao", "Transação",
            "realizada", "estabelecimento", "pagamento", "realizado",
            "saldo", "atualizado"
        )

        private val STOP_WORDS_LOWER = STOP_WORDS.map { it.lowercase() }

        private val PREFIXES = listOf(
            "você fez uma compra de",
            "transação realizada de",
            "pagamento efetuado de"
        )
    }
}