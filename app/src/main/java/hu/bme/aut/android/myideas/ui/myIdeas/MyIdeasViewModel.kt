package hu.bme.aut.android.myideas.ui.myIdeas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.ui.myIdeas.MyIdeasStateEvent.DeleteIdea
import hu.bme.aut.android.myideas.ui.myIdeas.MyIdeasStateEvent.LoadMyIdeas
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
    private val _dataState: MutableLiveData<DataState<List<Idea>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Idea>>>
        get() = _dataState

    fun setStateEvent(myIdeasStateEvent: MyIdeasStateEvent) {
        viewModelScope.launch {
            when (myIdeasStateEvent) {
                is LoadMyIdeas -> {
                    ideaRepository.getMyIdeasFromCache().onEach {
                        _dataState.value = it
                    }.launchIn(viewModelScope)
                }
                is DeleteIdea -> {
                    ideaRepository.deleteIdea(idea = myIdeasStateEvent.idea).onEach {
                        _dataState.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MyIdeasStateEvent {

    object LoadMyIdeas : MyIdeasStateEvent()
    data class DeleteIdea(val idea: Idea) : MyIdeasStateEvent()
}
