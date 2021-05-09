package hu.bme.aut.android.myideas.ui.newIdea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.ui.newIdea.NewIdeaStateEvent.LoadNewIdeaScreen
import hu.bme.aut.android.myideas.ui.newIdea.NewIdeaStateEvent.SaveIdea
import hu.bme.aut.android.myideas.ui.newIdea.dataState.NewIdeaDataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewIdeaViewModel
@Inject
constructor(
    private val ideaRepository: IdeaRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<NewIdeaDataState<Unit>> = MutableLiveData()

    val dataState: LiveData<NewIdeaDataState<Unit>>
        get() = _dataState

    fun setStateEvent(newIdeaStateEvent: NewIdeaStateEvent) {
        viewModelScope.launch {
            when (newIdeaStateEvent) {
                is LoadNewIdeaScreen -> {
                    _dataState.value = NewIdeaDataState.Success(Unit)
                }
                is SaveIdea -> {
                    if (newIdeaStateEvent.idea.id == "") {
                        ideaRepository.createIdea(newIdeaStateEvent.idea)
                            .onEach {
                                _dataState.value = it
                            }.launchIn(viewModelScope)
                    } else {
                        ideaRepository.updateIdea(newIdeaStateEvent.idea)
                            .onEach {
                                _dataState.value = it
                            }.launchIn(viewModelScope)
                    }
                }
            }
        }
    }
}

sealed class NewIdeaStateEvent {
    object LoadNewIdeaScreen : NewIdeaStateEvent()
    data class SaveIdea(val idea: Idea) : NewIdeaStateEvent()
}