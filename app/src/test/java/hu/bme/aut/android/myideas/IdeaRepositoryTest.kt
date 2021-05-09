package hu.bme.aut.android.myideas

import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.mappers.toCacheDTO
import hu.bme.aut.android.myideas.models.mappers.toCacheDTOWithoutGivingId
import hu.bme.aut.android.myideas.models.mappers.toListOfIdeaDomainModelFromCache
import hu.bme.aut.android.myideas.models.mappers.toNetworkDTO
import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.ui.newIdea.dataState.NewIdeaDataState
import hu.bme.aut.android.myideas.util.DataState
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first

class IdeaRepositoryTest : BehaviorSpec({
    val mockNetworkDataSource: NetworkDataSource = mockk()
    //val mockNetworkDataSource: MockNetworkDataSource = mockk()
    val mockCacheDataSource: IdeaDao = mockk()

    val ideaRepository = IdeaRepository(
        networkDataSource = mockNetworkDataSource,
        cacheDataSource = mockCacheDataSource
    )

    // Mocking the Idea this way is necessary, because the mockk tool could not use the custom
    // extension functions on a mockk object.
    val mockedIdea = Idea(
        id = "12345",
        title = "",
        shortDescription = "",
        description = ""
    )

    given(
        "Network sends an Idea when we call it and the Room insertion is successful."
    ) {
        coEvery { mockNetworkDataSource.getMyLastIdea() } returns
                IdeaNetworkDTO(
                    id = "12345",
                    title = "",
                    shortDescription = "",
                    description = ""
                )

        coEvery { mockCacheDataSource.insert(any()) } returns 1

        When("IdeaRepository's loadDashboard() method is called") {
            val returnValue = ideaRepository.loadDashboard()

            Then("Return value's firts value should be DataState.Loading") {
                returnValue.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("Return value's second value should be DataState.Success") {
                returnValue.drop(1).first() shouldBe DataState.Success(
                    Idea(
                        id = "12345",
                        title = "",
                        shortDescription = "",
                        description = ""
                    )
                )
            }
        }
    }

    given("There is a successful network and Room call during idea creation") {
        coEvery { mockNetworkDataSource.createMyIdea(mockedIdea.toNetworkDTO()) } just Runs

        coEvery { mockCacheDataSource.insert(mockedIdea.toCacheDTO()) } returns 1

        When("IdeaRepository's createIdea(Idea) method is called.") {
            val returnValue = ideaRepository.createIdea(mockedIdea)

            Then("Return value's first element should be NewIdeaDataState.Loading") {
                returnValue.first().shouldBeTypeOf<NewIdeaDataState.Loading>()
            }
            and("Return value's second element should be NewIdeaDataState.Success") {
                returnValue.drop(1).first()
                    .shouldBe(NewIdeaDataState.SuccessfulCreation(data = Unit))
            }
        }
    }

    given(
        "There is a list of Ideas returning from network by getMyIdeas() call and" +
                "there is a successful Room insertion"
    ) {
        coEvery { mockNetworkDataSource.getMyIdeas() } returns listOf(
            IdeaNetworkDTO(
                id = "12345",
                title = "",
                shortDescription = "",
                description = ""
            ),
            IdeaNetworkDTO(
                id = "12346",
                title = "",
                shortDescription = "",
                description = ""
            )
        )

        coEvery { mockCacheDataSource.insert(any()) } returns 1

        When("IdeaRepository's getMyIdeas() method is called.") {
            val returnValue = ideaRepository.getMyIdeas()

            Then("Return value's first element should be DataState.Loading") {
                returnValue.first().shouldBeTypeOf<DataState.Loading>()
            }
            and(
                "Return value's second value should be DataState.Success" +
                        "with a list of Idea in it."
            ) {
                returnValue.drop(1).first().shouldBe(
                    DataState.Success(
                        data = listOf(
                            Idea(
                                id = "12345",
                                title = "",
                                shortDescription = "",
                                description = ""
                            ),
                            Idea(
                                id = "12346",
                                title = "",
                                shortDescription = "",
                                description = ""
                            )
                        )
                    )
                )
            }
        }
    }

    given("There is a successful network and Room call during idea modification.") {
        coEvery { mockNetworkDataSource.updateMyIdea(mockedIdea.toNetworkDTO()) } just Runs

        coEvery { mockCacheDataSource.insert(mockedIdea.toCacheDTO()) } returns 1

        When("IdeaRepository's updateIdea(Idea) method is called") {
            val returnValue = ideaRepository.updateIdea(mockedIdea)

            Then("Return value's first value should be NewIdeaDataState.Loading") {
                returnValue.first().shouldBeTypeOf<NewIdeaDataState.Loading>()
            }
            and("Return value's second value should be NewIdeaDataState.Success") {
                returnValue.drop(1).first() shouldBe NewIdeaDataState.Success(Unit)
            }
        }
    }

    given("There is a successful network and Room call during idea deletion.") {
        coEvery { mockNetworkDataSource.deleteMyIdea(mockedIdea.id) } just Runs

        coEvery { mockCacheDataSource.delete(mockedIdea.toCacheDTOWithoutGivingId()) } just Runs

        coEvery {
            mockCacheDataSource.getAllIdeas().toListOfIdeaDomainModelFromCache()
        } returns listOf(
            Idea(
                id = "12345",
                title = "",
                shortDescription = "",
                description = ""
            ),
            Idea(
                id = "12346",
                title = "",
                shortDescription = "",
                description = ""
            )
        )

        When("IdeaRepository's deleteIdea(Idea) method is called") {
            val returnValue = ideaRepository.deleteIdea(mockedIdea)

            Then("Return value's first value should be DataState.Loading") {
                returnValue.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("Return value's second value should be DataState.Success") {
                returnValue.drop(1).first() shouldBe DataState.Success(
                    data =
                    listOf(
                        Idea(
                            id = "12345",
                            title = "",
                            shortDescription = "",
                            description = ""
                        ),
                        Idea(
                            id = "12346",
                            title = "",
                            shortDescription = "",
                            description = ""
                        )
                    )
                )
            }
        }
    }
}) {
    override fun isolationMode() = IsolationMode.InstancePerLeaf
}