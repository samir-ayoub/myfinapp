package com.knowledge.myfinapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.knowledge.myfinapp.data.model.TransactionEntity
import com.knowledge.myfinapp.data.model.TransactionWithDetails
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface TransactionDao {

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: String?): TransactionWithDetails?

    @Transaction
    @Query("SELECT * FROM transactions ORDER BY occurredAt DESC")
    fun observeTransactions(): Flow<List<TransactionWithDetails>>

    @Query("UPDATE transactions SET synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Upsert
    suspend fun upsert(entity: TransactionEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM transactions WHERE hash = :hash)")
    suspend fun existsByHash(hash: String): Boolean

    @Query("SELECT * FROM transactions WHERE synced = 0")
    suspend fun unsynced(): List<TransactionEntity>

    @Query("""
    UPDATE transactions SET
        amount = :amount,
        description = :description,
        merchantId = :merchantId,
        categoryId = :categoryId,
        occurredAt = :occurredAt,
        source = :source,
        hash = :hash,
        updatedAt = :updatedAt,
        synced = :synced
    WHERE id = :id
""")
    suspend fun update(
        id: String,
        amount: BigDecimal,
        description: String,
        merchantId: String?,
        categoryId: String?,
        occurredAt: Long,
        source: String,
        hash: String,
        updatedAt: Long,
        synced: Boolean
    )
}