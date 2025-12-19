package com.knowledge.myfinapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.knowledge.myfinapp.data.local.entity.ExpenseEntity
import com.knowledge.myfinapp.data.local.relation.ExpenseWithDetails
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getById(id: String?): ExpenseEntity?

    @Transaction
    @Query("SELECT * FROM expenses ORDER BY occurredAt DESC")
    fun observeExpenses(): Flow<List<ExpenseWithDetails>>

    @Query("UPDATE expenses SET synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: ExpenseEntity): Long

    @Upsert
    suspend fun upsert(entity: ExpenseEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM expenses WHERE hash = :hash)")
    suspend fun existsByHash(hash: String): Boolean

    @Query("SELECT * FROM expenses WHERE synced = 0")
    suspend fun unsynced(): List<ExpenseEntity>

    @Query("""
    UPDATE expenses SET
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