package com.example.movieandusers.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieandusers.data.db.dao.MoviesDao
import com.example.movieandusers.data.db.dao.UsersDao
import com.example.movieandusers.data.db.entities.MoviesEntity
import com.example.movieandusers.data.db.entities.UsersEntity

private const val CURRENT_VERSION = 1

private fun provideDatabase(application: Application): MovieAndUsersDB {
    return Room.databaseBuilder(application, MovieAndUsersDB::class.java, "MovieUsers.db")
        .build()
}

fun provideMoviesDao(database: MovieAndUsersDB): MoviesDao {
    return database.moviesDao()
}

fun provideUsersDao(database: MovieAndUsersDB): UsersDao {
    return database.usersDao()
}

@Database(entities = [MoviesEntity::class, UsersEntity::class], version = CURRENT_VERSION)
abstract class MovieAndUsersDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun usersDao(): UsersDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieAndUsersDB? = null

        fun getDatabase(context: Application): MovieAndUsersDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = provideDatabase(context)
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}