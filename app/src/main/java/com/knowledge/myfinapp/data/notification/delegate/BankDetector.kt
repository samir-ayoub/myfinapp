package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.Bank

interface BankDetector {
    fun detect(packageName: String, text: String): Bank?
}