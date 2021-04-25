package hu.bme.aut.android.myideas.datasources.cacheDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.models.cache.IdeaCacheDTO

@Database(
    entities = [
        IdeaCacheDTO::class,
    ], version = 2
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao

    companion object {
        const val DATABASE_NAME = "local_db"
    }
}