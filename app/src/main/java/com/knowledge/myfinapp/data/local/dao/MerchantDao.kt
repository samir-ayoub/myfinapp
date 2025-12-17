package com.knowledge.myfinapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.knowledge.myfinapp.data.local.entity.ExpenseEntity
import com.knowledge.myfinapp.data.local.entity.MerchantEntity

@Dao
interface MerchantDao {
    @Query("SELECT * FROM merchants WHERE id = :id")
    suspend fun getById(id: String): MerchantEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: MerchantEntity): Long

    @Upsert
    suspend fun upsert(entity: MerchantEntity)

    @Query("""
    UPDATE merchants SET
        name = :name,
        normalizedName = :normalizedName
    WHERE id = :id
    """)
    suspend fun update(
        id: String,
        name: String,
        normalizedName: String
    )
}