package com.knowledge.myfinapp.data.notification.impl

import com.knowledge.myfinapp.data.notification.model.ParsedExpenseData
import com.knowledge.myfinapp.data.notification.model.RawNotification
import com.knowledge.myfinapp.data.notification.parser.AmountExtractor
import com.knowledge.myfinapp.data.notification.parser.BankDetector
import com.knowledge.myfinapp.data.notification.parser.IgnoredStatus
import com.knowledge.myfinapp.data.notification.parser.MerchantNormalizer
import com.knowledge.myfinapp.data.notification.parser.NotificationParser
import com.knowledge.myfinapp.data.notification.parser.ParseError
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import timber.log.Timber

class NotificationParserImpl(
    private val bankDetector: BankDetector,
    private val amountExtractor: AmountExtractor,
    private val merchantNormalizer: MerchantNormalizer
    ): NotificationParser {

    override fun parse(notification: RawNotification): ParseResult {
        Timber.i("parse called")

        try {
            val bank = bankDetector.detect(
                notification.packageName,
                notification.text
            ) ?: return ParseResult.Ignored(IgnoredStatus.BANK_NOT_DETECTED)

            Timber.i("parsed bank: $bank")

            val amount = amountExtractor.extract(notification.text)
                ?: return ParseResult.Failure(
                    ParseError.AmountNotFound
                )

            Timber.i("parsed amount: $amount")

            val merchant = merchantNormalizer.normalize(notification.text)

            Timber.i("parsed merchant: $merchant")

            val data = ParsedExpenseData(
                bank = bank,
                amount = amount,
                merchantRaw = merchant,
                occurredAt = notification.postedAt,
                isDebit = true
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
