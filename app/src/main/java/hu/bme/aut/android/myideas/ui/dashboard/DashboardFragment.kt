package hu.bme.aut.android.myideas.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.NavigationHost
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.ui.about.AboutFragment
import hu.bme.aut.android.myideas.ui.myIdeas.MyIdeasFragment
import hu.bme.aut.android.myideas.ui.newIdea.NewIdeaFragment
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.layout_dashboard.view.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    companion object {
        const val LOADING = 0
        const val DASHBOARD_SCREEN = 1
        const val ERROR_SCREEN = 2
    }

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        view.dashboard_myIdeas_Button.setOnClickListener {
            (activity as NavigationHost).navigateTo(MyIdeasFragment(), true)
        }

        view.dashboard_newIdea_Button.setOnClickListener {
            (activity as NavigationHost).navigateTo(NewIdeaFragment(), true)
        }

        view.dashboard_About_Button.setOnClickListener {
            (activity as NavigationHost).navigateTo(AboutFragment(), true)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateEvent(DashboardStateEvent.LoadDashboard)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<String> -> {
                    setViewFlipper(DASHBOARD_SCREEN)
                    dashboard_textView.text = dataState.data
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
        fragment_dashboard_view_flipper.displayedChild = displayedState
    }
}