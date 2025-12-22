//package com.knowledge.myfinapp
//
//import android.app.Notification
//import android.os.Bundle
//import android.service.notification.StatusBarNotification
//import com.knowledge.myfinapp.system.listener.AndroidNotificationListener
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import java.time.Instant
//
//class AndroidNotificationListenerTest {
//
//    private lateinit var cut: AndroidNotificationListener
//
//    @BeforeEach
//    fun setup() {
//        cut = AndroidNotificationListener()
//    }
//
//    @Test
//    fun `maps StatusBarNotification to RawNotification correctly`() {
//        val extras = Bundle().apply {
//            putCharSequence(Notification.EXTRA_TEXT, "Payment of R$ 50")
//            putCharSequence(Notification.EXTRA_TITLE, "Nubank")
//        }
//
//        val notification = Notification().apply {
//            this.extras = extras
//        }
//
//        val sbn = mockk<StatusBarNotification> {
//            every { this@mockk.notification } returns notification
//            every { packageName } returns "com.bank.app"
//            every { postTime } returns 1_700_000_000_000
//        }
//
//        val result = cut.run { sbn.toRawNotification() }
//
//        assertNotNull(result)
//        assertEquals("com.bank.app", result?.packageName)
//        assertEquals("Nubank", result?.title)
//        assertEquals("Payment of R$ 50", result?.text)
//        assertEquals(
//            Instant.ofEpochMilli(1_700_000_000_000),
//            result?.postedAt
//        )
//    }
//
//    @Test
//    fun `returns null when notification text is missing`() {
//        val notification = Notification().apply {
//            extras = Bundle()
//        }
//
//        val sbn = mockk<StatusBarNotification> {
//            every { this@mockk.notification } returns notification
//        }
//
//        val result = cut.run { sbn.toRawNotification() }
//
//        assertNull(result)
//    }
//}
