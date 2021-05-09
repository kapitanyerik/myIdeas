package hu.bme.aut.android.myideas.datasources.networkDataSource

import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO

class MockNetworkDataSource : NetworkDataSource {
    override suspend fun getMyIdeas(): List<IdeaNetworkDTO> {
        return listOf(
            IdeaNetworkDTO(
                id = "11117",
                title = "third great idea",
                shortDescription = "this is the short description of the third idea",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                        "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                        "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                        "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                        "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                        "mollit anim id est laborum."
            ),
            IdeaNetworkDTO(
                id = "11116",
                title = "second great idea",
                shortDescription = "this is the short description of the second idea",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                        "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                        "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                        "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                        "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                        "mollit anim id est laborum."
            ),
            IdeaNetworkDTO(
                id = "11115",
                title = "first great idea",
                shortDescription = "this is the short description of the first idea",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                        "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                        "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                        "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                        "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                        "mollit anim id est laborum."
            )
        )
    }

    override suspend fun getMyLastIdea(): IdeaNetworkDTO {
        return IdeaNetworkDTO(
            id = "11117",
            title = "third great idea",
            shortDescription = "this is the short description of the third idea",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                    "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                    "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                    "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                    "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                    "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                    "mollit anim id est laborum."
        )
    }

    override suspend fun createMyIdea(idea: IdeaNetworkDTO) {
        return
    }

    override suspend fun updateMyIdea(idea: IdeaNetworkDTO) {
        return
    }

    override suspend fun deleteMyIdea(id: String) {
        return
    }
}