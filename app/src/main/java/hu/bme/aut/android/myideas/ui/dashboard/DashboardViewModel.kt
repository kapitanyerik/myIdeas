package hu.bme.aut.android.myideas.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val ideaRepository: IdeaRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Unit>> = MutableLiveData()

    val dataState: LiveData<DataState<Unit>>
        get() = _dataState

    fun setStateEvent(dashboardStateEvent: DashboardStateEvent) {
        viewModelScope.launch {
            when (dashboardStateEvent) {
                is DashboardStateEvent.LoadDashboard -> {
                    ideaRepository.loadDashboard()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class DashboardStateEvent {

    object LoadDashboard : DashboardStateEvent()
}
