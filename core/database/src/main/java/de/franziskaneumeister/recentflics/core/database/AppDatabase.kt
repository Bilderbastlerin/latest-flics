package de.franziskaneumeister.recentflics.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.franziskaneumeister.recentflics.core.database.models.MovieDbModel

@Database(entities = [MovieDbModel::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}