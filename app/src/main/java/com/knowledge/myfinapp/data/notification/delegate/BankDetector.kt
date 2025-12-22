package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.BankDetection

interface BankDetector {
    fun detect(packageName: String, text: String): BankDetection?
}