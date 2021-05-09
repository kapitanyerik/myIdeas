package hu.bme.aut.android.myideas.models.mappers

import hu.bme.aut.android.myideas.models.cache.IdeaCacheDTO
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.models.network.IdeaNetworkDTO
import java.util.*

fun Idea.toNetworkDTO() = IdeaNetworkDTO(
    id = UUID.randomUUID().toString(),
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun Idea.toNetworkDTOWithoutGivingId() = IdeaNetworkDTO(
    id = id,
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun Idea.toCacheDTO() = IdeaCacheDTO(
    id = UUID.randomUUID().toString(),
    title = title,
    shortDescription = shortDescription,
    description = description
)

fun Idea.toCacheDTOWithoutGivingId() = IdeaCacheDTO(
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

fun List<IdeaNetworkDTO>.toListOfIdeaDomainModelFromNetwork() = this.map { it.toIdeaDomainModel() }

fun List<IdeaCacheDTO>.toListOfIdeaDomainModelFromCache() = this.map { it.toIdeaDomainModel() }
