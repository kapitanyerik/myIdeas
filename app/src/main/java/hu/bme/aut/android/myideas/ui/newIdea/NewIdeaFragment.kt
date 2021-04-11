package hu.bme.aut.android.myideas.ui.newIdea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.android.synthetic.main.fragment_new_idea.*

@AndroidEntryPoint
class NewIdeaFragment : Fragment() {

    companion object {
        const val LOADING = 0
        const val NEW_IDEA_SCREEN = 1
        const val ERROR_SCREEN = 2
    }

    private val viewModel: NewIdeaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_idea, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateEvent(NewIdeaStateEvent.LoadNewIdeaScreen)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<Unit> -> {
                    setViewFlipper(NEW_IDEA_SCREEN)
                }

                is DataState.Error -> {
                    setViewFlipper(ERROR_SCREEN)
                }

                is DataState.Loading -> {
                    setViewFlipper(LOADING)
                }
            }
        }
    }

    private fun setViewFlipper(displayedState: Int) {
        fragment_new_idea_view_flipper.displayedChild = displayedState
    }
}