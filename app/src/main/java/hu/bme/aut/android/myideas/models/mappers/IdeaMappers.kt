package hu.bme.aut.android.myideas.models.mappers

import hu.bme.aut.android.myideas.models.cache.IdeaCacheDTO
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO

fun Idea.toNetworkDTO() = IdeaNetworkDTO(
    id = id,
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun Idea.toCacheDTO() = IdeaCacheDTO(
    id = id,
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun IdeaNetworkDTO.toIdeaDomainModel() = Idea(
    id = id,
    title = title ?: "",
    description = description ?: "",
    shortDescription = shortDescription ?: ""
)

fun IdeaCacheDTO.toIdeaDomainModel() = Idea(
    id = id,
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun List<IdeaNetworkDTO>.toListOfIdeaDomainModel() = this.map { it.toIdeaDomainModel() }
