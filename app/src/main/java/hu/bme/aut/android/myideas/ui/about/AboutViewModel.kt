package hu.bme.aut.android.myideas.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel
@Inject
constructor() : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Unit>> = MutableLiveData()

    val dataState: LiveData<DataState<Unit>>
        get() = _dataState

    fun setStateEvent(aboutStateEvent: AboutStateEvent) {
        viewModelScope.launch {
            when (aboutStateEvent) {
                is AboutStateEvent.LoadAbout -> {
                    _dataState.value = DataState.Success(data = Unit)
                }
            }
        }
    }
}

sealed class AboutStateEvent {

    object LoadAbout : AboutStateEvent()
}