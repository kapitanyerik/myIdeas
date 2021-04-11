package hu.bme.aut.android.myideas.ui.myIdeas

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
class MyIdeasViewModel
@Inject
constructor(
    private val ideaRepository: IdeaRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Unit>> = MutableLiveData()

    val dataState: LiveData<DataState<Unit>>
        get() = _dataState

    fun setStateEvent(myIdeasStateEvent: MyIdeasStateEvent) {
        viewModelScope.launch {
            when (myIdeasStateEvent) {
                is MyIdeasStateEvent.LoadMyIdeas -> {
                    _dataState.value = DataState.Success(data = Unit)
                }
            }
        }
    }
}

sealed class MyIdeasStateEvent {

    object LoadMyIdeas : MyIdeasStateEvent()
}
