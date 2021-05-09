package hu.bme.aut.android.myideas.datasources.cacheDataSource.daos

import androidx.room.*
import hu.bme.aut.android.myideas.models.cache.IdeaCacheDTO

@Dao
interface IdeaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: IdeaCacheDTO): Long

    @Query("SELECT * FROM ideas")
    suspend fun getAllIdeas(): List<IdeaCacheDTO>

    @Query("SELECT * FROM ideas WHERE id=:id")
    suspend fun getIdea(id: String): IdeaCacheDTO?

    @Query("SELECT * FROM ideas LIMIT 1")
    suspend fun getMyLastIdea(): IdeaCacheDTO?

    @Delete
    suspend fun delete(idea: IdeaCacheDTO)
}