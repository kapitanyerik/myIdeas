package hu.bme.aut.android.myideas.datasources.networkDataSource

import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.network.IdeaNetworkEntity
import retrofit2.http.*

interface NetworkDataSource {
    @GET("myIdeas")
    suspend fun getMyIdeas(): List<Idea>

    @POST("myIdeas")
    suspend fun createMyIdea(
        @Body idea: Idea
    )

    @PUT("myIdeas/{id}")
    suspend fun updateMyIdea(
        @Path("id") id: String,
        @Body idea: Idea
    ): IdeaNetworkEntity

    @DELETE("myIdeas/{id}")
    suspend fun deleteMyIdea(
        @Path("id") id: String
    )
}