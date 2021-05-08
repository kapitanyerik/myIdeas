package hu.bme.aut.android.myideas.repositories

import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.mappers.toCacheDTO
import hu.bme.aut.android.myideas.models.mappers.toListOfIdeaDomainModel
import hu.bme.aut.android.myideas.models.mappers.toNetworkDTO
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    suspend fun createIdea(idea: Idea): Flow<DataState<Unit>> {
        return flow {
            emit(DataState.Loading)
            networkDataSource.createMyIdea(idea.toNetworkDTO())
            cacheDataSource.insert(idea.toCacheDTO())
            emit(DataState.Success(Unit))
        }
            .catch { throwable ->
                emit(DataState.Error(throwable.message ?: "Error during new idea creation process"))
            }
    }

    suspend fun getMyIdeas(): Flow<DataState<Idea>> {
        return flow {
            emit(DataState.Loading)
            val myIdeas = networkDataSource.getMyIdeas().toListOfIdeaDomainModel()
            myIdeas.forEach {
                cacheDataSource.insert(it.toCacheDTO())
                emit(DataState.Success(it))
            }
        }
            .catch { throwable ->
                emit(DataState.Error(throwable.message ?: "Error during getting my ideas process"))
            }
    }

    suspend fun updateIdea(idea: Idea): Flow<DataState<Unit>> {
        return flow {
            emit(DataState.Loading)
            with(idea) {
                networkDataSource.updateMyIdea(toNetworkDTO())
                cacheDataSource.insert(toCacheDTO())
                emit(DataState.Success(Unit))
            }
        }
            .catch { throwable ->
                emit(
                    DataState.Error(
                        throwable.message ?: "Error during updating one of my ideas process"
                    )
                )
            }
    }

    suspend fun deleteIdea(idea: Idea): Flow<DataState<Unit>> {
        return flow {
            emit(DataState.Loading)
            networkDataSource.deleteMyIdea(idea.id)
            cacheDataSource.delete(idea.toCacheDTO())
            emit(DataState.Success(Unit))
        }
            .catch { throwable ->
                emit(
                    DataState.Error(
                        throwable.message ?: "Error during deleting one of my ideas process"
                    )
                )
            }
    }
}