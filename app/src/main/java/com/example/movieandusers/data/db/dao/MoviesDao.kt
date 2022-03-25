package com.example.movieandusers.data.db.dao

import androidx.room.*
import com.example.movieandusers.data.db.entities.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviesEntity: MoviesEntity)

    /**
     * Use this to observe DB changes
     */
    @Transaction
    @Query("SELECT * FROM movies")
    fun findAllByFlow(): Flow<List<MoviesEntity>?>

    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun findAll(): List<MoviesEntity>?

    @Query("SELECT * FROM movies")
    suspend fun findById(): MoviesEntity?

    @Transaction
    suspend fun deleteAllAndInsert(list:List<MoviesEntity>){
        deleteAll()
        list.forEach {
            insert(it)
        }
    }

    @Query("DELETE FROM movies")
    fun deleteAll()

}