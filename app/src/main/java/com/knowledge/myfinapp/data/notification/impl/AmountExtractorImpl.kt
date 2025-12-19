package com.knowledge.myfinapp.data.notification.impl

import com.knowledge.myfinapp.data.notification.parser.AmountExtractor
import java.math.BigDecimal

class AmountExtractorImpl: AmountExtractor {
    override fun extract(text: String): BigDecimal? {
        val normalized = text
            .replace("\n", " ")
            .replace("\t", " ")

        return  extractByRegex(normalized)
    }

    private fun extractByRegex(normalized: String,): BigDecimal? {
        val match = EUR_AMOUNT_REGEX.find(normalized) ?: return null
        val rawValue = match.groupValues[1]

        return rawValue
            .replace(".", "")
            .replace(",", ".")
            .toBigDecimalOrNull()
    }

    companion object {
        val BR_AMOUNT_REGEX =
        Regex("""R\$ ?([\d\.]+,\d{2})""")

        private val EUR_AMOUNT_REGEX =
            Regex("""(?:€|EUR)?\s?([\d\.]+,\d{2})\s?(?:€|EUR)?""", RegexOption.IGNORE_CASE)

    }
}