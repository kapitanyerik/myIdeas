package hu.bme.aut.android.myideas.ui.myIdeas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.NavigationHost
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.util.DataState
import kotlinx.android.synthetic.main.fragment_my_ideas.*
import kotlinx.android.synthetic.main.layout_my_ideas.*

@AndroidEntryPoint
class MyIdeasFragment : Fragment(), MyIdeasListAdapter.ItemClickedListener {

    companion object {
        const val LOADING = 0
        const val MY_IDEAS_SCREEN = 1
        const val ERROR_SCREEN = 2
    }

    private val viewModel: MyIdeasViewModel by viewModels()
    private lateinit var myIdeasListAdapter: MyIdeasListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<Idea> -> {
                    setViewFlipper(MY_IDEAS_SCREEN)
                    myIdeasListAdapter.submitList(listOf(dataState.data))
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_ideas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStateEvent(MyIdeasStateEvent.LoadMyIdeas)
        setupToolbar()
        setupList()
    }

    private fun setupToolbar() {
        myIdeasToolbar.setNavigationOnClickListener {
            (activity as NavigationHost).navigateBack()
        }
    }

    private fun setupList() {
        myIdeasListAdapter = MyIdeasListAdapter()
        myIdeasList.adapter = myIdeasListAdapter
        myIdeasListAdapter.itemClickedListener = this
    }

    override fun onListItemClicked(item: Idea) {
        TODO()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Szerkesztés" -> {
                TODO()
            }
            "Törlés" -> {
                TODO()
            }
        }
        return super.onContextItemSelected(item)
    }
}