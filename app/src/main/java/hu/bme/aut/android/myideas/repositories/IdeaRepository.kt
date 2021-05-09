package hu.bme.aut.android.myideas.repositories

import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.mappers.*
import hu.bme.aut.android.myideas.ui.newIdea.dataState.NewIdeaDataState
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class IdeaRepository
constructor(

    // -- This property is used for mocking the network layer.
    // -- When I made the video about the application I used that one.
    //private val networkDataSource: MockNetworkDataSource = MockNetworkDataSource(),

    // -- Alternatively you can use this networkDataSource. The only problem with this, is that it does
    // -- working because there is no backend system behind the application.
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: IdeaDao
) {
    suspend fun loadDashboard(): Flow<DataState<Idea>> =
        flow {
            emit(DataState.Loading)
            //val idea = networkDataSource.getMyLastIdea().toIdeaDomainModel()
            //cacheDataSource.insert(idea.toCacheDTO())
            val idea = cacheDataSource.getMyLastIdea()?.toIdeaDomainModel()
            idea?.let {
                emit(DataState.Success(it))
            } ?: emit(DataState.Error("There is no last idea in the cache"))
        }
            .catch { throwable ->
                emit(
                    DataState.Error(
                        throwable.message ?: "Error during getting my last idea process"
                    )
                )
            }

    suspend fun createIdea(idea: Idea): Flow<NewIdeaDataState<Unit>> =
        flow {
            emit(NewIdeaDataState.Loading)
            with(idea) {
                networkDataSource.createMyIdea(this.toNetworkDTO())
                cacheDataSource.insert(this.toCacheDTO())
            }
            emit(NewIdeaDataState.SuccessfulCreation(Unit))
        }
            .catch { throwable ->
                emit(
                    NewIdeaDataState.Error(
                        throwable.message ?: "Error during new idea creation process"
                    )
                )
            }

    // This network call is used only when the network layer is not just a mocked class.
    suspend fun getMyIdeas(): Flow<DataState<List<Idea>>> =
        flow {
            emit(DataState.Loading)
            val myIdeas: List<Idea> =
                networkDataSource.getMyIdeas().toListOfIdeaDomainModelFromNetwork()
            myIdeas.forEach {
                cacheDataSource.insert(it.toCacheDTO())
            }
            emit(DataState.Success(myIdeas))
        }
            .catch { throwable ->
                emit(DataState.Error(throwable.message ?: "Error during getting my ideas process"))
            }

    // This call is used only when the network layer is just a mocked class.
    suspend fun getMyIdeasFromCache(): Flow<DataState<List<Idea>>> =
        flow {
            emit(DataState.Loading)
            val myIdeas = cacheDataSource.getAllIdeas().toListOfIdeaDomainModelFromCache()
            emit(DataState.Success(myIdeas))
        }
            .catch { throwable ->
                emit(DataState.Error(throwable.message ?: "Error during getting my ideas process"))
            }

    suspend fun updateIdea(idea: Idea): Flow<NewIdeaDataState<Unit>> =
        flow {
            emit(NewIdeaDataState.Loading)
            with(idea) {
                networkDataSource.updateMyIdea(toNetworkDTOWithoutGivingId())
                cacheDataSource.insert(toCacheDTOWithoutGivingId())
            }
            emit(NewIdeaDataState.SuccessfulUpdate(Unit))
        }
            .catch { throwable ->
                emit(
                    NewIdeaDataState.Error(
                        throwable.message ?: "Error during updating one of my ideas process"
                    )
                )
            }

    suspend fun deleteIdea(idea: Idea): Flow<DataState<List<Idea>>> =
        flow {
            emit(DataState.Loading)
            with(idea) {
                networkDataSource.deleteMyIdea(this.id)
                cacheDataSource.delete(this.toCacheDTOWithoutGivingId())
            }
            val myIdeas = cacheDataSource.getAllIdeas().toListOfIdeaDomainModelFromCache()
            emit(DataState.Success(myIdeas))
        }
            .catch { throwable ->
                emit(
                    DataState.Error(
                        throwable.message ?: "Error during deleting one of my ideas process"
                    )
                )
            }
}