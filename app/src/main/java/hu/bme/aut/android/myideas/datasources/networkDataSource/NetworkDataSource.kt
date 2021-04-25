package hu.bme.aut.android.myideas.datasources.networkDataSource

import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO
import retrofit2.http.*

interface NetworkDataSource {
    @GET("myIdeas")
    suspend fun getMyIdeas(): List<IdeaNetworkDTO>

    @POST("myIdea")
    suspend fun createMyIdea(
        @Body idea: IdeaNetworkDTO
    )

    @PUT("myIdea")
    suspend fun updateMyIdea(
        @Path("id") id: String,
        @Body idea: IdeaNetworkDTO
    )

    @DELETE("myIdea/{id}")
    suspend fun deleteMyIdea(
        @Path("id") id: String
    )
}