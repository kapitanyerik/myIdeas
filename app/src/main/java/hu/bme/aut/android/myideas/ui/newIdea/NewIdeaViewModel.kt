package hu.bme.aut.android.myideas.ui.newIdea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewIdeaViewModel
@Inject
constructor(
    private val ideaRepository: IdeaRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Unit>> = MutableLiveData()

    val dataState: LiveData<DataState<Unit>>
        get() = _dataState

    fun setStateEvent(newIdeaStateEvent: NewIdeaStateEvent) {
        viewModelScope.launch {
            when (newIdeaStateEvent) {
                is NewIdeaStateEvent.LoadNewIdeaScreen -> {
                    _dataState.value = DataState.Success(data = Unit)
                }
            }
        }
    }
}

sealed class NewIdeaStateEvent {

    object LoadNewIdeaScreen : NewIdeaStateEvent()
}