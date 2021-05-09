package hu.bme.aut.android.myideas.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.NavigationHost
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.layout_about.*

@AndroidEntryPoint
class AboutFragment : Fragment() {

    companion object {
        const val LOADING = 0
        const val ABOUT_SCREEN = 1
        const val ERROR_SCREEN = 2
    }

    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<Unit> -> {
                    setViewFlipper(ABOUT_SCREEN)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStateEvent(AboutStateEvent.LoadAbout)
        setupToolbar()
    }

    private fun setupToolbar() {
        aboutToolbar.setNavigationOnClickListener {
            (activity as NavigationHost).navigateBack()
        }
    }

    private fun setViewFlipper(displayedState: Int) {
        fragment_about_view_flipper.displayedChild = displayedState
    }
}