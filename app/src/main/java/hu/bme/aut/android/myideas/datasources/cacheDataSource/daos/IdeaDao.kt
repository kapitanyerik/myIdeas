package hu.bme.aut.android.myideas.datasources.cacheDataSource.daos

import androidx.room.*
import hu.bme.aut.android.myideas.models.cache.IdeaCacheEntity

@Dao
interface IdeaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: IdeaCacheEntity): Long

    @Query("SELECT * FROM ideas")
    suspend fun getAllIdeas(): List<IdeaCacheEntity>

    @Query("SELECT * FROM ideas WHERE id=:id")
    suspend fun getIdea(id: String): IdeaCacheEntity?

    @Delete
    suspend fun delete(idea: IdeaCacheEntity)
}