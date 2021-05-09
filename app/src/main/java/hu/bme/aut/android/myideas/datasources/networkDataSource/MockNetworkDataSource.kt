package hu.bme.aut.android.myideas.datasources.networkDataSource

import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO

class MockNetworkDataSource : NetworkDataSource {
    override suspend fun getMyIdeas(): List<IdeaNetworkDTO> {
        return listOf(
            IdeaNetworkDTO(
                id = "11115",
                title = "",
                shortDescription = "",
                description = ""
            ),
            IdeaNetworkDTO(
                id = "11116",
                title = "",
                shortDescription = "",
                description = ""
            ),
            IdeaNetworkDTO(
                id = "11117",
                title = "",
                shortDescription = "",
                description = ""
            )
        )
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