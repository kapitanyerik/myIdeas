package hu.bme.aut.android.myideas

import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.mappers.toCacheDTO
import hu.bme.aut.android.myideas.models.mappers.toNetworkDTO
import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO
import hu.bme.aut.android.myideas.repositories.IdeaRepository
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
    val mockCacheDataSource: IdeaDao = mockk()

    val ideaRepository = IdeaRepository(
        networkDataSource = mockNetworkDataSource,
        cacheDataSource = mockCacheDataSource
    )

    val mockedIdea = Idea(
        id = "12345",
        title = "",
        shortDescription = "",
        description = ""
    )

    given("There is a successful network call during idea creation") {
        coEvery { mockNetworkDataSource.createMyIdea(mockedIdea.toNetworkDTO()) } just Runs

        coEvery { mockCacheDataSource.insert(mockedIdea.toCacheDTO()) } returns 1

        When("IdeaRepository's createIdea(Idea) method is called.") {
            val returnValue = ideaRepository.createIdea(mockedIdea)

            Then("The return value's first element should be DataState.Loading") {
                returnValue.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("The return value's second element should be DataState.Success") {
                returnValue.drop(1).first().shouldBe(DataState.Success(data = Unit))
            }
        }
    }

    given("There is a list of Ideas returning from network") {
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

            Then("The return value's first element should be DataState.Loading") {
                returnValue.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("The return value's second value should be DataState.Success with an Idea in it.") {
                returnValue.drop(1).first().shouldBeTypeOf<DataState.Success<Idea>>()
            }
            and("The return value's third value should be DataState.Success with an Idea in it.") {
                returnValue.drop(2).first().shouldBe(
                    DataState.Success(
                        data = Idea(
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