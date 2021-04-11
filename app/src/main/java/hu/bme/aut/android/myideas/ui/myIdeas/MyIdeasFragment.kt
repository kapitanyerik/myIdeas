package hu.bme.aut.android.myideas.ui.myIdeas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.android.synthetic.main.fragment_my_ideas.*

@AndroidEntryPoint
class MyIdeasFragment : Fragment() {

    companion object {
        const val LOADING = 0
        const val MY_IDEAS_SCREEN = 1
        const val ERROR_SCREEN = 2
    }

    private val viewModel: MyIdeasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ideas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateEvent(MyIdeasStateEvent.LoadMyIdeas)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<Unit> -> {
                    setViewFlipper(MY_IDEAS_SCREEN)
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
        fragment_my_ideas_view_flipper.displayedChild = displayedState
    }
}