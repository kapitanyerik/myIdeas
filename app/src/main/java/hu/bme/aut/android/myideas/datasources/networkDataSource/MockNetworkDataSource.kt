package hu.bme.aut.android.myideas.datasources.networkDataSource

import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO

class MockNetworkDataSource : NetworkDataSource {
    override suspend fun getMyIdeas(): List<IdeaNetworkDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun createMyIdea(idea: IdeaNetworkDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMyIdea(idea: IdeaNetworkDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMyIdea(id: String) {
        TODO("Not yet implemented")
    }
}