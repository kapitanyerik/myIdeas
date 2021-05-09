package hu.bme.aut.android.myideas.ui.newIdea.dataState

sealed class NewIdeaDataState<out R> {
    data class SuccessfulCreation<out T>(val data: T) : NewIdeaDataState<T>()
    data class SuccessfulUpdate<out T>(val data: T) : NewIdeaDataState<T>()
    data class Success<out T>(val data: T) : NewIdeaDataState<T>()
    data class Error(val message: String) : NewIdeaDataState<Nothing>()
    object Loading : NewIdeaDataState<Nothing>()
}