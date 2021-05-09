package hu.bme.aut.android.myideas.models.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Idea(
    val id: String,
    val title: String,
    val description: String,
    val shortDescription: String
) : Parcelable
