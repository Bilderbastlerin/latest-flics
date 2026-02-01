package de.franziskaneumeister.recentflics.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.franziskaneumeister.recentflics.core.database.models.MovieDbModel
import de.franziskaneumeister.recentflics.core.types.entities.MovieId

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertAll(movies: List<MovieDbModel>)

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    public fun pagingSource(query: String): PagingSource<MovieId, MovieDbModel>

    @Query("DELETE FROM movies")
    public suspend fun clearAll()
}