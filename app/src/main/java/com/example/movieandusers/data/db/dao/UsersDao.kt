package com.example.movieandusers.data.db.dao

import androidx.room.*
import com.example.movieandusers.data.db.entities.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviesEntity: UsersEntity)

    /**
     * Use this to observe DB changes
     */
    @Transaction
    @Query("SELECT * FROM users")
    fun findAllByFlow(): Flow<List<UsersEntity>?>

}