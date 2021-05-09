package hu.bme.aut.android.myideas.ui.myIdeas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyIdeasViewModel
@Inject
constructor(
    private val ideaRepository: IdeaRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Idea>> = MutableLiveData()

    val dataState: LiveData<DataState<Idea>>
        get() = _dataState

    fun setStateEvent(myIdeasStateEvent: MyIdeasStateEvent) {
        viewModelScope.launch {
            when (myIdeasStateEvent) {
                is MyIdeasStateEvent.LoadMyIdeas -> {
                    ideaRepository.getMyIdeas().onEach {
                        _dataState.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MyIdeasStateEvent {

    object LoadMyIdeas : MyIdeasStateEvent()
}
