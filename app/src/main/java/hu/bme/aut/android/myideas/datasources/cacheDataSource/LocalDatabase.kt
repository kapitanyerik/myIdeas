package hu.bme.aut.android.myideas.datasources.cacheDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.models.cache.IdeaCacheEntity

@Database(
    entities = [
        IdeaCacheEntity::class,
    ], version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao

    companion object {
        const val DATABASE_NAME = "local_db"
    }
}