package com.knowledge.myfinapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.knowledge.myfinapp.data.model.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: CategoryEntity): Long

    @Upsert
    suspend fun upsert(entity: CategoryEntity)

    @Query("""
    UPDATE categories SET
        name = :name,
        color = :color
    WHERE id = :id
    """)
    suspend fun update(
        id: String,
        name: String,
        color: String
    )
}