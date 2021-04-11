package hu.bme.aut.android.myideas.repositories

import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class IdeaRepository
constructor(
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: IdeaDao
) {
    suspend fun loadDashboard() = flow {
        emit(DataState.Loading)
        delay(1000)
        emit(DataState.Success(data = "Hello initialized Dashboard"))
    }
}