package hu.bme.aut.android.myideas.models.mappers

import hu.bme.aut.android.myideas.models.cache.IdeaCacheDTO
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO

class IdeaMapper {
    fun Idea.toNetworkDTO() = IdeaNetworkDTO(
        id, title, description, shortDescription
    )

    fun Idea.toCacheDTO() = IdeaCacheDTO(
        id, title, description, shortDescription
    )

    fun IdeaNetworkDTO.toIdeaDomainModel() = Idea(
        id = id,
        title = title ?: "",
        description = description ?: "",
        shortDescription = shortDescription ?: ""
    )

    fun IdeaCacheDTO.toIdeaDomainModel() = Idea(
        id, title, description, shortDescription
    )

    fun List<IdeaNetworkDTO>.toListOfIdeaDomainModel() = this.map { it.toIdeaDomainModel() }
}