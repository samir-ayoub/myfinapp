package com.knowledge.myfinapp.data.notification.parser

import com.knowledge.myfinapp.data.notification.model.Bank

interface BankDetector {
    fun detect(packageName: String, text: String): Bank?
}