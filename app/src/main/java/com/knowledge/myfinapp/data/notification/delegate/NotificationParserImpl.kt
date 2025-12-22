package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.ParsedNotification
import com.knowledge.myfinapp.data.model.ParsingEvidence
import com.knowledge.myfinapp.data.model.RawNotification
import com.knowledge.myfinapp.data.model.SourceType
import com.knowledge.myfinapp.data.notification.parser.IgnoredStatus
import com.knowledge.myfinapp.data.notification.parser.ParseError
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import timber.log.Timber

class NotificationParserImpl(
    private val bankDetector: BankDetector,
    private val amountExtractor: AmountExtractor,
    private val merchantNormalizer: MerchantNormalizer,
    ): NotificationParser {

    override fun parse(notification: RawNotification): ParseResult {
        Timber.i("parse called")

        try {
            val bankDetection = bankDetector.detect(
                notification.packageName,
                notification.text
            ) ?: return ParseResult.Ignored(IgnoredStatus.BANK_NOT_DETECTED)

            Timber.i("parsed bank: ${bankDetection.bank}")

            val amount = amountExtractor.extract(notification.text)
                ?: return ParseResult.Failure(
                    ParseError.AmountNotFound
                )

            Timber.i("parsed amount: $amount")

            val merchant = merchantNormalizer.normalize(notification.text)

            Timber.i("parsed merchant: $merchant")

            val data = ParsedNotification(
                bank = bankDetection.bank,
                amount = amount,
                merchantRaw = merchant,
                occurredAt = notification.postedAt,
                isDebit = true,
                evidences = ParsingEvidence(
                    bankDetectedByPackageName = bankDetection.source == SourceType.PACKAGE_NAME,
                    true,
                    merchantDetected = !merchant.isNullOrBlank()
                )
            )

            return ParseResult.Success(data)
        } catch (t: Throwable) {
            Timber.e(
                t,
                "Unexpected error while parsing notification: $notification"
            )
            return ParseResult.Failure(
                ParseError.Unexpected(t)
            )
        }
    }
}
